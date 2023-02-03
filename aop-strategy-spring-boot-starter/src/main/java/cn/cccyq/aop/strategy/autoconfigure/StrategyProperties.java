package cn.cccyq.aop.strategy.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("aop.strategy")
public class StrategyProperties {

    private boolean enabled = true;

    private String implement = "thread-local";

    public StrategyProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setImplement(String implement) {
        this.implement = implement;
    }

}
