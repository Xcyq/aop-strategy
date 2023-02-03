package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Strategy("proto1")
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Proto1Impl implements Interface1{

    @Override
    public String hello(String name) {
        return "proto1" + name;
    }

    @Override
    public String fly() {
        return null;
    }

}
