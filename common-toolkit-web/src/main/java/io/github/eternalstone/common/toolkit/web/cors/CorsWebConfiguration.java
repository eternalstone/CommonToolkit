package io.github.eternalstone.common.toolkit.web.cors;

import io.github.eternalstone.common.toolkit.web.annotation.EnableGlobalCrossOrigin;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Map;

/**
 * 全局跨域配置
 *
 * @author Justzone on 2023/10/7 14:26
 */

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Configuration
@RequiredArgsConstructor
public class CorsWebConfiguration {

    private final ApplicationContext applicationContext;

    @Bean
    public FilterRegistrationBean<CorsOrginFilter> corsOrginFilter() {
        //获取全局注解属性值
        Map<String, Object> objectMap = applicationContext
                .getBeansWithAnnotation(EnableGlobalCrossOrigin.class);
        //全局注解只对一个bean生效
        Map.Entry<String, Object> entry = objectMap.entrySet().iterator().next();
        Object value = entry.getValue();
        Class<?> clazz = AopUtils.getTargetClass(value);
        //获取到注解对象
        EnableGlobalCrossOrigin globalCrossOrigin = clazz.getDeclaredAnnotation(EnableGlobalCrossOrigin.class);
        CorsOriginProperties properties = new CorsOriginProperties();
        properties.setMaxAge(globalCrossOrigin.maxAge());
        properties.setCredentials(globalCrossOrigin.credentials());
        if (globalCrossOrigin.origins().length > 0) {
            properties.setOrigins(Arrays.asList(globalCrossOrigin.origins()));
        }
        if (globalCrossOrigin.allowedMethods().length > 0) {
            properties.setAllowedMethods(StringUtils.join(globalCrossOrigin.allowedMethods(), ","));
        } else {
            properties.setAllowedHeaders(CorsConfiguration.ALL);
        }
        if (globalCrossOrigin.allowedHeaders().length > 0) {
            properties.setAllowedHeaders(StringUtils.join(globalCrossOrigin.allowedHeaders(), ","));
        } else {
            properties.setAllowedHeaders(CorsConfiguration.ALL);
        }
        FilterRegistrationBean<CorsOrginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsOrginFilter(properties));
        registrationBean.setName("corsOrginFilter");
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
