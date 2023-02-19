package cn.cccyq.aop.strategy.proxy;

import cn.cccyq.aop.strategy.interceptor.DefaultStrategyMethodInterceptor;
import org.springframework.util.StringUtils;

/**
 * 为带有策略注解({@link cn.cccyq.aop.strategy.annotation.Strategy}和{@link cn.cccyq.aop.strategy.annotation.Strategy})
 * 的aop代理配置上方法拦截器{@link DefaultStrategyMethodInterceptor}
 *
 * @author cyq
 * @since 2023-02-19 12:03:39
 */
public class DefaultStrategyAnnotationAutoProxyCreator extends StrategyAnnotationAutoProxyCreator {

    @Override
    public String getInterceptorName() {
        return StringUtils.uncapitalize(DefaultStrategyMethodInterceptor.class.getSimpleName());
    }

}
