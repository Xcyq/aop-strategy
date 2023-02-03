package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.stereotype.Service;

@Service
@Strategy({"r2"})
public class Impl2 implements Interface1 {

    @Override
    public String hello(String name) {
        return "hello2" + name;
    }

    @Override
    public String fly() {
        return "fly2";
    }

}
