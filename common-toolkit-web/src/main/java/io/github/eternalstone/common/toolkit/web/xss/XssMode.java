package io.github.eternalstone.common.toolkit.web.xss;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 9:47
 */
public enum XssMode {

    /**
     * 清理
     */
    CLEAR,
    /**
     * 转义
     */
    ESCAPE,
    /**
     * 校验，抛出异常
     */
    VALIDATE

}
