package cn.cccyq.aop.strategy.proxy;

import cn.cccyq.aop.strategy.interceptor.DefaultStrategyMethodInterceptor;
import org.springframework.util.StringUtils;

public class DefaultStrategyAnnotationAutoProxyCreator extends StrategyAnnotationAutoProxyCreator {

    @Override
    public String getInterceptorName() {
        return StringUtils.uncapitalize(DefaultStrategyMethodInterceptor.class.getSimpleName());
    }

}
