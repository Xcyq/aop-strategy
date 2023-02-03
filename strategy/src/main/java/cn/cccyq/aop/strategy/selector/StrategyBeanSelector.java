package cn.cccyq.aop.strategy.selector;

import cn.cccyq.aop.strategy.support.ArrayUtil;
import cn.cccyq.aop.strategy.support.RoutingKeyUtil;
import cn.cccyq.aop.strategy.support.StrategyAnnotationUtil;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class StrategyBeanSelector {

    private final ListableBeanFactory beanFactory;

    public StrategyBeanSelector(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object selectTargetObject(Object defaultTargetObject, String[] contextRoutingKeys) {
        if (ArrayUtil.isEmpty(contextRoutingKeys)) {
            // 上下文没有路由
            return defaultTargetObject;
        }

        Class<?> targetClass = AopUtils.getTargetClass(defaultTargetObject);
        Class<?>[] targetClassInterfaces = targetClass.getInterfaces();
        if (ArrayUtil.isEmpty(targetClassInterfaces)) {
            // 目标类没有实现接口
            return defaultTargetObject;
        }

        Class<?> targetInterface = targetClassInterfaces[0];
        Map<String, ?> beansOfType = beanFactory.getBeansOfType(targetInterface);

        for (Object bean : beansOfType.values()) {
            Class<?> noproxyClass = AopUtils.getTargetClass(bean);
            if (!StrategyAnnotationUtil.isAnnotatedStrategy(noproxyClass)) {
               // 即使都实现了相同的接口也可能没有加上策略注解
               continue;
            }

            List<String[]> annotatedRoutingKeys = StrategyAnnotationUtil.getAnnotatedRoutingKeys(noproxyClass);
            if (!CollectionUtils.isEmpty(annotatedRoutingKeys)) {
                for (String[] annotatedRoutingKey : annotatedRoutingKeys) {
                    if (RoutingKeyUtil.isMatch(contextRoutingKeys, annotatedRoutingKey)) {
                        if (AopUtils.isAopProxy(bean)) {
                            return AopProxyUtils.getSingletonTarget(bean);
                        } else {
                            return bean;
                        }
                    }
                }
            }
        }

        return defaultTargetObject;
    }

}
