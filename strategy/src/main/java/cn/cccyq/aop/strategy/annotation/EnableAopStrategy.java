package cn.cccyq.aop.strategy.annotation;

import cn.cccyq.aop.strategy.interceptor.DefaultStrategyMethodInterceptor;
import cn.cccyq.aop.strategy.proxy.DefaultStrategyAnnotationAutoProxyCreator;
import cn.cccyq.aop.strategy.proxy.StrategyAnnotationAutoProxyCreator;
import cn.cccyq.aop.strategy.proxy.StrategyAutoProxyRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使用此注解开启aop-strategy
 * @author cyq
 * @since 2023-01-27 21:43:39
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(StrategyAutoProxyRegistrar.class)
public @interface EnableAopStrategy {

    /**
     * 自动代理创建器
     * 默认的自动代理创建器将使用默认的aop方法拦截实现
     * @see StrategyAnnotationAutoProxyCreator
     * @see DefaultStrategyAnnotationAutoProxyCreator
     * @see DefaultStrategyMethodInterceptor
     */
    Class<?> autoProxyCreator() default DefaultStrategyAnnotationAutoProxyCreator.class;

}
