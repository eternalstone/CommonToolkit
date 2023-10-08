package io.github.eternalstone.common.toolkit.web.advice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 15:46
 */
@Configuration
public class GlobalExceptionConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
