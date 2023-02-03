package cn.cccyq.aop.strategy;

import cn.cccyq.aop.strategy.route.ThreadLocalRoutingKeysHolder;
import cn.cccyq.aop.strategy.test.Ctl1;
import cn.cccyq.aop.strategy.test.stage.version.Ctrl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class StrategyTest {

    private AnnotationConfigApplicationContext context = null;
    
    private ThreadLocalRoutingKeysHolder routingKeysHolder = new ThreadLocalRoutingKeysHolder();

    @Before
    public void before() {
        context = new AnnotationConfigApplicationContext("cn.cccyq");
    }

    @Test
    public void test() {
        Ctl1 ctl1 = context.getBean(Ctl1.class);
        Assert.assertEquals("hello1?", ctl1.hello("?"));
        Assert.assertEquals("fly1", ctl1.fly());

        routingKeysHolder.addRoutingKey("1");
        Assert.assertEquals("hello1!", ctl1.hello("!"));
        Assert.assertEquals("fly1", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("r2");
        Assert.assertEquals("hello2aa", ctl1.hello("aa"));
        Assert.assertEquals("fly2", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("r1");
        Assert.assertEquals("hello1bb", ctl1.hello("bb"));
        Assert.assertEquals("fly1", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("r1");
        routingKeysHolder.addRoutingKey("r3");
        Assert.assertEquals("hello3zz", ctl1.hello("zz"));
        Assert.assertEquals("fly3", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("r2");
        Assert.assertEquals("hello2zz", ctl1.hello("zz"));
        Assert.assertEquals("fly2", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();
    }

    @Test
    public void testExtends() {
        Ctl1 ctl1 = context.getBean(Ctl1.class);
        routingKeysHolder.addRoutingKey("r3sub");
        Assert.assertEquals("hello3sub88", ctl1.hello("88"));
        Assert.assertEquals("fly3sub", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("r3subsub");
        Assert.assertEquals("hello3subsub99", ctl1.hello("99"));
        Assert.assertEquals("fly3subsub", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("mr1");
        Assert.assertEquals("hellomr1&mr211", ctl1.hello("11"));
        Assert.assertEquals("flymr1&mr2", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();
    }

    @Test
    public void testMultiStrategy() {
        Ctl1 ctl1 = context.getBean(Ctl1.class);
        routingKeysHolder.addRoutingKey("mr2");
        Assert.assertEquals("hellomr1&mr233", ctl1.hello("33"));
        Assert.assertEquals("flymr1&mr2", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("mr3");
        Assert.assertEquals("hellomr3&mr433", ctl1.hello("33"));
        Assert.assertEquals("flymr3&mr4", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("mr4");
        Assert.assertEquals("hellomr3&mr499", ctl1.hello("99"));
        Assert.assertEquals("flymr3&mr4", ctl1.fly());
        routingKeysHolder.removeAllRoutingKeys();
    }

    @Test
    public void testStage1() {
        Ctrl bean = context.getBean(Ctrl.class);

        routingKeysHolder.addRoutingKey("v1");
        Assert.assertEquals("v1", bean.version());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("v2");
        Assert.assertEquals("v2", bean.version());
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("v1");
        Assert.assertEquals("v1", bean.version());
        routingKeysHolder.removeAllRoutingKeys();
    }

    @Test
    public void testProto() {
        Ctl1 bean = context.getBean(Ctl1.class);

        routingKeysHolder.addRoutingKey("proto1");
        Assert.assertEquals("proto1haha", bean.hello("haha"));
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("proto2");
        Assert.assertEquals("proto2333", bean.hello("333"));
        routingKeysHolder.removeAllRoutingKeys();

        routingKeysHolder.addRoutingKey("proto1");
        Assert.assertEquals("proto1555", bean.hello("555"));
        routingKeysHolder.removeAllRoutingKeys();
    }

}
