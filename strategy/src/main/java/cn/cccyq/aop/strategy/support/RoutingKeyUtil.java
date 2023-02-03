package cn.cccyq.aop.strategy.support;

import java.util.Objects;

/**
 * @author cyq
 * @since 2023-01-27 22:16:37
 */
public class RoutingKeyUtil {

    public static boolean isMatch(String[] leftRoutingKeys, String[] rightRoutingKeys) {
        if (ArrayUtil.isEmpty(leftRoutingKeys)
                || ArrayUtil.isEmpty(rightRoutingKeys)
                || rightRoutingKeys.length != leftRoutingKeys.length) {
            return false;
        }

        for (int i = 0; i < leftRoutingKeys.length; i++) {
            if (!Objects.equals(rightRoutingKeys[i], leftRoutingKeys[i])) {
                return false;
            }
        }
        return true;
    }

}
