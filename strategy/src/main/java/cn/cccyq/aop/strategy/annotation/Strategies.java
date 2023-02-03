package cn.cccyq.aop.strategy.annotation;

import java.lang.annotation.*;

/**
 * 多策略注解
 *
 * @author cyq
 * @since 2023-01-27 21:47:50
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Strategies {

    /**
     * @return 0个1个或多个策略
     */
    Strategy[] value();

}
