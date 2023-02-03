package cn.cccyq.aop.strategy.test.stage.version;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Version1
@Service
@Primary
public class Version1Impl implements IVersion{

    @Override
    public String version() {
        return "v1";
    }
}
