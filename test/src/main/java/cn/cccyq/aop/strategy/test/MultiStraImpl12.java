package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.stereotype.Service;

@Service
@Strategy({"mr1"})
@Strategy({"mr2"})
public class MultiStraImpl12 implements Interface1 {

    @Override
    public String hello(String name) {
        return "hellomr1&mr2" + name;
    }

    @Override
    public String fly() {
        return "flymr1&mr2";
    }

}

