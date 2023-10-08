package io.github.eternalstone.common.toolkit.web.annotation;

import io.github.eternalstone.common.toolkit.web.cors.CorsWebConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.cors.CorsConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(CorsWebConfiguration.class)
public @interface EnableGlobalCrossOrigin {

    @AliasFor("origins")
    String[] value() default {};

    @AliasFor("value")
    String[] origins() default {};

    String[] allowedHeaders() default {};

    String[] allowedMethods() default {};

    boolean credentials() default true;

    long maxAge() default -1L;

}
