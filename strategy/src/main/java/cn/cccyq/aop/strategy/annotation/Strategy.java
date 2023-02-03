package cn.cccyq.aop.strategy.annotation;

import java.lang.annotation.*;

/**
 * 策略注解
 *
 * @author cyq
 * @since 2023-01-27 21:46:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Strategies.class)
public @interface Strategy {

    /**
     * @return 策略的路由键
     */
    String[] value() default {};

}
