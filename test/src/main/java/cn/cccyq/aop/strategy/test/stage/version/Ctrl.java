package cn.cccyq.aop.strategy.test.stage.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Ctrl {

    @Autowired
    private IVersion version;

    public String version() {
        return version.version();
    }

}
