package cn.cccyq.aop.strategy.test.stage.version;

import org.springframework.stereotype.Service;

@Version2
@Service
public class Version2Impl implements IVersion{

    @Override
    public String version() {
        return "v2";
    }
}
