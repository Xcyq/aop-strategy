package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.stereotype.Service;

@Service
@Strategy({"r1", "r3"})
public class Impl3 implements Interface1 {

    @Override
    public String hello(String name) {
        return "hello3" + name;
    }

    @Override
    public String fly() {
        return "fly3";
    }

}
