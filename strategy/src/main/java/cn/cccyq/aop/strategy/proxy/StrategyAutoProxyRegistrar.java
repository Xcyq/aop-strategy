package cn.cccyq.aop.strategy.proxy;

import cn.cccyq.aop.strategy.annotation.EnableAopStrategy;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 为了在生命周期早期就注册自动代理创建器
 * @see org.springframework.context.annotation.AspectJAutoProxyRegistrar 灵感来源 AspectJAutoProxyRegistrar
 * @author cyq
 * @since 2023-01-27 21:38:36
 */
public class StrategyAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Class<?> autoProxyCreator = null;
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableAopStrategy.class.getName(), false);
        if (!CollectionUtils.isEmpty(annotationAttributes)) {
            Object implementObj = annotationAttributes.get("autoProxyCreator");
            if (implementObj != null) {
                autoProxyCreator = (Class<?>) implementObj;
            }
        }

        RootBeanDefinition beanDefinition = new RootBeanDefinition(autoProxyCreator);
        beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(StringUtils.uncapitalize(autoProxyCreator.getSimpleName()), beanDefinition);
    }

}
