package io.github.eternalstone.common.toolkit.web.xss;

import io.github.eternalstone.common.toolkit.util.ObjectUtils;
import io.github.eternalstone.common.toolkit.web.annotation.EnableXssDefence;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * to do something
 * 拦截器的作用是判断是否需要忽略xss过滤
 * 表单参数通过controlerAdivce的数据绑定实现xss数据过滤
 * json参数通过jackson的反序列化实现xss的数据过滤
 * 提供util工具类实现标签过滤
 *
 * @author Justzone on 2023/10/7 17:16
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Configuration
@RequiredArgsConstructor
public class XssDefenceConfiguration implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    /**
     * 配置项
     *
     * @return
     */
    @Bean
    public XssProperties xssProperties() {
        XssProperties properties = new XssProperties();
        Map<String, Object> objectMap = applicationContext
                .getBeansWithAnnotation(EnableXssDefence.class);
        if (ObjectUtils.isEmpty(objectMap)) {
            properties.setEnabled(false);
            return properties;
        }
        //全局注解只对一个bean生效
        Map.Entry<String, Object> entry = objectMap.entrySet().iterator().next();
        Object value = entry.getValue();
        Class<?> clazz = AopUtils.getTargetClass(value);
        //获取到注解对象
        EnableXssDefence xssDefence = clazz.getDeclaredAnnotation(EnableXssDefence.class);
        properties.setTrim(xssDefence.trim());
        properties.setMode(xssDefence.mode());
        properties.setEnableEscape(xssDefence.escape());
        properties.setPrettyPrint(xssDefence.prettyPrint());
        properties.setPathPatterns(Arrays.asList(xssDefence.includes()));
        properties.setPathExcludePatterns(Arrays.asList(xssDefence.excludes()));
        return properties;
    }

    /**
     * 清理器
     */
    @Bean
    @ConditionalOnMissingBean
    public XssCleaner xssCleaner(XssProperties xssProperties) {
        return new DefaultXssCleaner(xssProperties);
    }

    /**
     * 表单清理器
     */
    @Bean
    public XssFormClean xssFormClean(XssProperties xssProperties,
                                     XssCleaner xssCleaner) {
        return new XssFormClean(xssProperties, xssCleaner);
    }

    /**
     * json清理器
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer(XssProperties xssProperties,
                                                                      XssCleaner xssCleaner) {
        return builder -> builder.deserializerByType(String.class, new XssJsonClean(xssProperties, xssCleaner));
    }

    /**
     * 全局拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        XssProperties properties = xssProperties();
        List<String> patterns = properties.getPathPatterns();
        if (patterns.isEmpty()) {
            patterns.add("/**");
        }
        //添加全局拦截器
        XssInterceptor interceptor = new XssInterceptor(properties);
        registry.addInterceptor(interceptor)
                .addPathPatterns(patterns)
                .excludePathPatterns(properties.getPathExcludePatterns())
                .order(Ordered.LOWEST_PRECEDENCE);
    }

}
