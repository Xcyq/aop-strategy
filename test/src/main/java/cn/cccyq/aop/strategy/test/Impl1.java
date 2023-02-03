package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Strategy({"r1"})
public class Impl1 implements Interface1 {

    @Override
    public String hello(String name) {
        return "hello1" + name;
    }

    @Override
    public String fly() {
        return "fly1";
    }

}
