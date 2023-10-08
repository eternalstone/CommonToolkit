package io.github.eternalstone.common.toolkit.web.xss;

import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 9:17
 */
public class XssHolder {
    private static final ThreadLocal<XssIgnore> XSS_IGNORE = new ThreadLocal<>();

    /**
     * 是否开启
     *
     * @return boolean
     */
    public static boolean isEnabled() {
        return Objects.isNull(XSS_IGNORE.get());
    }

    /**
     * 判断是否被忽略
     */
    static boolean isIgnore(String name) {
        XssIgnore xssIgnore = XSS_IGNORE.get();
        if (xssIgnore == null) {
            return false;
        }
        String[] ignoreArray = xssIgnore.value();
        // 1. 如果没有设置忽略的字段
        if (ignoreArray.length == 0) {
            return true;
        }
        // 2. 指定忽略的属性
        return ObjectUtils.containsElement(ignoreArray, name);
    }

    /**
     * 标记为开启
     */
    static void setIgnore(XssIgnore xssIgnore) {
        XSS_IGNORE.set(xssIgnore);
    }

    /**
     * 关闭 xss 清理
     */
    public static void remove() {
        XSS_IGNORE.remove();
    }

}
