package cn.cccyq.aop.strategy.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Ctl1 {

    @Autowired
    private Interface1 interface1;

    public String hello(String name) {
        return interface1.hello(name);
    }

    public String fly() {
        return interface1.fly();
    }

}
