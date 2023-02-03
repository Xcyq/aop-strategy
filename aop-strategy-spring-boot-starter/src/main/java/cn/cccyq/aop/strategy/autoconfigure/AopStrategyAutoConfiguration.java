package cn.cccyq.aop.strategy.autoconfigure;

import cn.cccyq.aop.strategy.annotation.EnableAopStrategy;
import cn.cccyq.aop.strategy.interceptor.DefaultStrategyMethodInterceptor;
import cn.cccyq.aop.strategy.interceptor.StrategyMethodInterceptor;
import cn.cccyq.aop.strategy.route.ContextRoutingKeysHolder;
import cn.cccyq.aop.strategy.route.ThreadLocalRoutingKeysHolder;
import cn.cccyq.aop.strategy.selector.StrategyBeanSelector;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAopStrategy
@EnableConfigurationProperties
@Import(StrategyProperties.class)
@ConditionalOnProperty(prefix = "aop.strategy", name = "enabled", havingValue = "true")
public class AopStrategyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(StrategyBeanSelector.class)
    public StrategyBeanSelector strategyBeanSelector(ListableBeanFactory listableBeanFactory) {
        return new StrategyBeanSelector(listableBeanFactory);
    }

    @Bean
    @ConditionalOnMissingBean(StrategyMethodInterceptor.class)
    public StrategyMethodInterceptor defaultStrategyMethodInterceptor(StrategyBeanSelector strategyBeanSelector,
                                                                          ContextRoutingKeysHolder contextRoutingKeysHolder) {
        return new DefaultStrategyMethodInterceptor(strategyBeanSelector, contextRoutingKeysHolder);
    }

    @Bean
    @ConditionalOnProperty(prefix = "aop.strategy", name = "implement", havingValue = "thread-local")
    @ConditionalOnMissingBean(ContextRoutingKeysHolder.class)
    public ContextRoutingKeysHolder threadLocalRoutingKeysHolder() {
        return new ThreadLocalRoutingKeysHolder();
    }

}
