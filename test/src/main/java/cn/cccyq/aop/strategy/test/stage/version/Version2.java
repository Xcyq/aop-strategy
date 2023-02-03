package cn.cccyq.aop.strategy.test.stage.version;

import cn.cccyq.aop.strategy.annotation.Strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Strategy("v2")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Version2 {
}
