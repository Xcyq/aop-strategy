package cn.cccyq.aop.strategy.route;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ThreadLocalRoutingKeysHolder implements ContextRoutingKeysHolder {

    private static final ThreadLocal<List<String>> routingKeysThreadLocal = new ThreadLocal<>();

    public List<String> getCurrent() {
        List<String> routingKeysList = routingKeysThreadLocal.get();
        if (CollectionUtils.isEmpty(routingKeysList)) {
            routingKeysList = new ArrayList<>();
            routingKeysThreadLocal.set(routingKeysList);
        }
        return routingKeysList;
    }

    @Override
    public void addRoutingKey(String routingKey) {
        List<String> routingKeysList = getCurrent();
        routingKeysList.add(routingKey);
    }

    @Override
    public void addRoutingKeys(Collection<String> routingKeys) {
        List<String> routingKeysList = getCurrent();
        routingKeysList.addAll(routingKeys);
    }

    @Override
    public String[] getRoutingKeys() {
        List<String> routingKeysList = routingKeysThreadLocal.get();
        return CollectionUtils.isEmpty(routingKeysList) ? null : routingKeysList.toArray(new String[]{});
    }

    @Override
    public void removeAllRoutingKeys() {
        routingKeysThreadLocal.remove();
    }

}
