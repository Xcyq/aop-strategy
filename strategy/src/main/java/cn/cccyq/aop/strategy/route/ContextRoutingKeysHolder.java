package cn.cccyq.aop.strategy.route;

import java.util.Collection;

public interface ContextRoutingKeysHolder {

    void addRoutingKey(String routingKey);

    void addRoutingKeys(Collection<String> routingKeys);

    String[] getRoutingKeys();

    void removeAllRoutingKeys();

}
