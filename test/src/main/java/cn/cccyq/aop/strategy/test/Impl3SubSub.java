package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.stereotype.Service;

@Service
@Strategy({"r3subsub"})
public class Impl3SubSub extends Impl3Sub {

    @Override
    public String hello(String name) {
        return "hello3subsub" + name;
    }

    @Override
    public String fly() {
        return "fly3subsub";
    }

}
