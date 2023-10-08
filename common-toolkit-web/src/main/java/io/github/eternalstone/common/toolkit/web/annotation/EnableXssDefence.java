package io.github.eternalstone.common.toolkit.web.annotation;

import io.github.eternalstone.common.toolkit.web.xss.XssDefenceConfiguration;
import io.github.eternalstone.common.toolkit.web.xss.XssMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 16:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(XssDefenceConfiguration.class)
public @interface EnableXssDefence {

    /**
     * 是否全局trim文本
     */
    boolean trim() default false;

    /**
     * xss清理模式
     */
    XssMode mode() default XssMode.CLEAR;

    /**
     * [clear 专用] 是否保留换行
     */
    boolean prettyPrint() default false;

    /**
     * [clear 专用] 使用转义，默认关闭
     */
    boolean escape() default false;

    /**
     * 拦截xss请求路径
     */
    String[] includes() default { "/**" };

    /**
     * 放行xss请求路径
     */
    String[] excludes() default {};

}
