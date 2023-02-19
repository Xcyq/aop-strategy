package cn.cccyq.aop.strategy.route;

import java.util.Collection;

/**
 * 持有上下文路由键
 *
 * @author cyq
 * @since 2023-02-19 12:09:31
 */
public interface ContextRoutingKeysHolder {

    void addRoutingKey(String routingKey);

    void addRoutingKeys(Collection<String> routingKeys);

    String[] getRoutingKeys();

    void removeAllRoutingKeys();

}
