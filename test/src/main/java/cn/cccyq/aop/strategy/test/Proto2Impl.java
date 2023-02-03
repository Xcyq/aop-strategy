package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Strategy("proto2")
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Proto2Impl implements Interface1{

    @Override
    public String hello(String name) {
        return "proto2" + name;
    }

    @Override
    public String fly() {
        return null;
    }

}
