package cn.cccyq.aop.strategy.support;

import cn.cccyq.aop.strategy.annotation.Strategies;
import cn.cccyq.aop.strategy.annotation.Strategy;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyq
 * @since 2023-01-27 22:16:45
 */
public class StrategyAnnotationUtil {

    public static boolean isAnnotatedStrategy(Class<?> clazz) {
        boolean annotatedStrategies = AnnotationUtils.findAnnotation(clazz, Strategies.class) != null;
        boolean annotatedStrategy = AnnotationUtils.findAnnotation(clazz, Strategy.class) != null;
        return annotatedStrategies || annotatedStrategy;
    }

    public static List<String[]> getAnnotatedRoutingKeys(Class<?> annotatedStrategyClass) {
        List<String[]> routingKeysList = new ArrayList<>();

        Strategies strategies = AnnotationUtils.findAnnotation(annotatedStrategyClass, Strategies.class);
        if (strategies != null) {
            Strategy[] strategiesValue = strategies.value();
            if (ArrayUtil.isNotEmpty(strategiesValue)) {
                for (Strategy strategyInStrategies : strategiesValue) {
                    routingKeysList.add(strategyInStrategies.value());
                }
            }
        }

        Strategy strategy = AnnotationUtils.findAnnotation(annotatedStrategyClass, Strategy.class);
        if (strategy != null) {
            routingKeysList.add(strategy.value());
        }

        return routingKeysList;
    }

}
