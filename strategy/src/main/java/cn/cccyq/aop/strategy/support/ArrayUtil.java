package cn.cccyq.aop.strategy.support;

/**
 * @author cyq
 * @since 2023-01-27 21:34:01
 */
public class ArrayUtil {

    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isNotEmpty(Object[] arr) {
        return !isEmpty(arr);
    }

}
