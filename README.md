# aop-strategy
A tools for strategy coding based on spring-aop in Java

基于spring-aop实现的策略化类调用

## 如何使用

### 准备工作

1. 下载源码到本地

2. 在源码根目录执行（如果你有私库的话，可以把install替换成deploy）

   1. `mvn install strategy`
   2. `mvn install aop-strategy-spring-boot-starter`

3. 在你业务代码pom中引入

   ```xml
   <dependency>
       <groupId>cn.cccyq</groupId>
       <artifactId>aop-strategy-spring-boot-starter</artifactId>
       <version>1.0.0</version>
   </dependency>
   ```

### 代码使用

#### 场景1 版本控制

增加版本策略注解

```java
@Strategy("v1")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Version1 {
}

@Strategy("v2")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Version2 {
}
```

改造业务代码

```java
@Service
public class Ctrl {
    @Autowired
    private IVersion version
    public String version() {
        return version.version();
    }
}


public interface IVersion {
    String version();
}


@Version1
@Service
@Primary
public class Version1Impl implements IVersion{
    @Override
    public String version() {
        return "v1";
    }
}


@Version2
@Service
public class Version2Impl implements IVersion{
    @Override
    public String version() {
        return "v2";
    }
}

```

调用（单元测试）

```java
private AnnotationConfigApplicationContext context = null;

@Before
public void before() {
    context = new AnnotationConfigApplicationContext("cn.cccyq");
}    

@Test
public void testStage1() {
    Ctrl bean = context.getBean(Ctrl.class);

    StrategyThreadLocal.put("v1");
    Assert.assertEquals("v1", bean.version());
    StrategyThreadLocal.removeAll();

    StrategyThreadLocal.put("v2");
    Assert.assertEquals("v2", bean.version());
    StrategyThreadLocal.removeAll();

    StrategyThreadLocal.put("v1");
    Assert.assertEquals("v1", bean.version());
    StrategyThreadLocal.removeAll();
}
```

## 项目由来

在我们日常业务开发中，经常会遇到策略化的问题。例如，一个接口有多个实现，当不同请求进来时走不同实现。我们在spring环境中通常会采用如下方式实现策略模式：

```java
@Controller
public class MyController {

    @Autowired
    private Map<String, IService> serviceMap;
    
    public void doService(String strategy) {
        return serviceMap.getOrDefault(strategy, DefaultServiceImpl.class).doSomething();
    }
}
```

上述代码是让spring在依赖注入时，将实现`IService`的Bean都注入到serviceMap。其中，key是beanName，value是具体bean。而后在调用前通过策略名称在map搜索实现类，如果没找到就使用默认的实现类，然后执行业务逻辑。

该方法简单易用好理解，且无第三方依赖。缺点是，当某个实现类既属于A策略，又属于B策略时就遇到了麻烦。一般解决方案是给实现类取多个beanName。

**有没有一种方法能让像普通业务代码开发时那样无感知地调用service，但又可以悄悄地根据策略走不同的实现类呢**？像这样：

```java
@Controller
public class MyController {

    @Autowired
    private IService service;
    
    public void doService(String strategy) {
        return service.doSomething();
    }
}
```

但是我们怎么告诉spring我们要走什么strategy？所以还得“增加一点逻辑”：

```java
@Controller
public class MyController {

    @Autowired
    private IService service;
    
    public void beforDoService(String strategy) {
        switch(strategy){
            case "qq": return qqService.doSomething();
            case "wechat": return wechatService.doSomething();
        }
    }
    
    public void doService(String strategy) {
        return beforDoService(strategy);
    }
}
```

看出来了吗，这不就是aop吗？

于是乎我们可以……

## 设计

### 概念

策略，是一个组业务逻辑的抽象。

实现类，是接口interface的具体实现。

路由键，是策略的“地址”，通过路由键可以找到策略。

#### 策略匹配规则

1. 1个实现类可对应0到n个策略
2. 1个策略可对应0到n个路由键

用伪代码说话：

##### 一般场景

```java
public interface ISmsService {} // 接口

// 一般场景
@Strategy(routingKeys = {"qq"}) // 策略注解，写明策略的路由键为qq，只要匹配"qq"就会走到该实现类
public class QqSmsService implements ISmsService {} // QQ的具体实现类

// 一般场景
@Strategy(routingKeys = {"wechat"}) // 策略注解，写明策略的路由键为wechat，只要匹配"wechat"就会走到该实现类
public class WechatSmsService implements ISmsService {} // Wechat的具体实现类
```

##### 级联场景

```java
// 级联场景
@Strategy(routingKeys = {"fujian", "fuzhou"}) // 策略注解，写明策略的路由键为“福建福州”。"fujian""fuzhou"依次匹配才可走到该实现类
public class FujianFuzhouSmsService implements ISmsService {} // 福建福州的具体实现类
```

##### 多策略场景

分别注解

```java
// 多策略场景
// 比如当前有版本1、2、3，旧版本1、2需要走旧逻辑
@Strategy(routingKeys = {"version1"}) // 策略注解，路由键为version1
@Strategy(routingKeys = {"version2"}) // 策略注解，路由键为version2
public class OldVersionSmsService implements ISmsService {} // 旧版本（1、2）的具体实现类
```

合并注解

```java
// 多策略合并注解
@Strategy(routingKeys = {"version1"})
@Strategy(routingKeys = {"version2"})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OldVersion { // 自定义注解，并在此注解上添加@Strategy
}

@OldVersion
public class OldVersionSmsService implements ISmsService {} // 旧版本（1、2）的具体实现类
```

### 思路

依据aop思想，在实现类调用前进行拦截。判断当前调用需要走哪个策略，并获取到该策略类然后执行。

大致步骤如下：

1. 将所有需要策略化的类生成代理类并注入BeanFactory
2. 自定义实现aop拦截器
   1. 判断当前是否走策略，走哪个策略
   2. 找到该策略的实现类
   3. 通过该实现类执行方法