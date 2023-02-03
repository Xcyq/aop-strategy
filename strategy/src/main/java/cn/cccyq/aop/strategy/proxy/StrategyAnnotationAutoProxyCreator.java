package cn.cccyq.aop.strategy.proxy;

import cn.cccyq.aop.strategy.interceptor.ConfigurableInterceptorName;
import cn.cccyq.aop.strategy.support.StrategyAnnotationUtil;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

/**
 * 自动代理创建器，仅为类上带有@Strategy注解的Bean创建代理
 * @author cyq
 * @since 2023-01-27 21:48:23
 */
public abstract class StrategyAnnotationAutoProxyCreator extends AbstractAutoProxyCreator implements ConfigurableInterceptorName {

    public StrategyAnnotationAutoProxyCreator() {
        this.setProxyTargetClass(true);
        this.setExposeProxy(true);
        this.setInterceptorNames(getInterceptorName());
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource)
            throws BeansException {
        return StrategyAnnotationUtil.isAnnotatedStrategy(beanClass) ? PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS : DO_NOT_PROXY;
    }

}
