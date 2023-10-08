package io.github.eternalstone.common.toolkit.web.xss;

import java.lang.annotation.*;

/**
 * 忽略 xss
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XssIgnore {

    /**
     * 支持指定忽略的字段，感谢 pig（冷冷）提出的需求
     *
     * @return 字段数组
     */
    String[] value() default {};

}
