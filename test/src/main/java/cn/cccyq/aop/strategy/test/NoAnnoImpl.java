package cn.cccyq.aop.strategy.test;

import org.springframework.stereotype.Service;

@Service
public class NoAnnoImpl implements Interface1 {

    @Override
    public String hello(String name) {
        return "hello1" + name;
    }

    @Override
    public String fly() {
        return "fly1";
    }

}
