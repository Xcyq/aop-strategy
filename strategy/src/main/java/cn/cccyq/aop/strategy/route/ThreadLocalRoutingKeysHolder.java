package cn.cccyq.aop.strategy.route;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 默认实现，通过ThreadLocal持有上下文路由键。 <br>
 * 你也可以使用transmittable-thread-local维护上下文路由键，这样就可以在线程和线程池环境进行传递了。
 *
 * @author cyq
 * @since 2023-02-19 12:09:31
 */
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
