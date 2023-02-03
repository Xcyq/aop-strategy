package cn.cccyq.aop.strategy.test;

import cn.cccyq.aop.strategy.annotation.EnableAopStrategy;
import cn.cccyq.aop.strategy.interceptor.DefaultStrategyMethodInterceptor;
import cn.cccyq.aop.strategy.route.ContextRoutingKeysHolder;
import cn.cccyq.aop.strategy.route.ThreadLocalRoutingKeysHolder;
import cn.cccyq.aop.strategy.selector.StrategyBeanSelector;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAopStrategy
public class TestConfiguration {

    @Bean
    public StrategyBeanSelector strategyBeanSelector(ListableBeanFactory beanFactory) {
        return new StrategyBeanSelector(beanFactory);
    }

    @Bean
    public ContextRoutingKeysHolder threadLocalRoutingKeysHolder() {
        return new ThreadLocalRoutingKeysHolder();
    }

    @Bean
    public DefaultStrategyMethodInterceptor defaultStrategyMethodInterceptor(StrategyBeanSelector strategyBeanSelector,
                                                                             ContextRoutingKeysHolder contextRoutingKeysHolder) {
        return new DefaultStrategyMethodInterceptor(strategyBeanSelector, contextRoutingKeysHolder);
    }

}
