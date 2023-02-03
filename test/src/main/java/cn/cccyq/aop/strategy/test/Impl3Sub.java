package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.stereotype.Service;

@Service
@Strategy({"r3sub"})
public class Impl3Sub extends Impl3 {

    @Override
    public String hello(String name) {
        return "hello3sub" + name;
    }

    @Override
    public String fly() {
        return "fly3sub";
    }

}
