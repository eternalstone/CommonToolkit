package io.github.eternalstone.common.toolkit.web.annotation;

import io.github.eternalstone.common.toolkit.web.trace.TraceIdConfiguration;
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
@Import(TraceIdConfiguration.class)
public @interface EnableTraceId {
}
