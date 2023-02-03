package cn.cccyq.aop.strategy.interceptor;

import cn.cccyq.aop.strategy.route.ContextRoutingKeysHolder;
import cn.cccyq.aop.strategy.selector.StrategyBeanSelector;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;

/**
 * @author cyq
 * @since 2023-01-27 21:34:55
 */
public class DefaultStrategyMethodInterceptor implements StrategyMethodInterceptor {

    protected StrategyBeanSelector strategyBeanSelector;

    protected ContextRoutingKeysHolder contextRoutingKeysHolder;

    public DefaultStrategyMethodInterceptor(StrategyBeanSelector selector, ContextRoutingKeysHolder contextRoutingKeysHolder) {
        this.strategyBeanSelector = selector;
        this.contextRoutingKeysHolder = contextRoutingKeysHolder;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object o = invocation.getThis();
        Object[] arguments = invocation.getArguments();

        String[] contextRoutingKeys = getContextRoutingKeys();
        Object targetObject = getTargetObject(o, contextRoutingKeys);

        Method invocableMethod = AopUtils.selectInvocableMethod(invocation.getMethod(), targetObject.getClass());
        return invocableMethod.invoke(targetObject, arguments);
    }

    public Object getTargetObject(Object defaultTargetObject, String[] contextRoutingKeys) {
        return strategyBeanSelector.selectTargetObject(defaultTargetObject, contextRoutingKeys);
    }

    public String[] getContextRoutingKeys() {
        return contextRoutingKeysHolder.getRoutingKeys();
    }

}
