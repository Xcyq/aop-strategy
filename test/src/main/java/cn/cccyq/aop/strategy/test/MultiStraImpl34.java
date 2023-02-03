package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.Strategies;
import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.stereotype.Service;

@Service
@Strategies({@Strategy("mr3"), @Strategy("mr4")})
public class MultiStraImpl34 implements Interface1 {

    @Override
    public String hello(String name) {
        return "hellomr3&mr4" + name;
    }

    @Override
    public String fly() {
        return "flymr3&mr4";
    }

}

