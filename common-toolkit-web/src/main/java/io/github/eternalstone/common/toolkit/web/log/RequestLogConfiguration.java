package io.github.eternalstone.common.toolkit.web.log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * 动态配置请求日志打印
 *
 * @author Justzone on 2023/10/7 16:41
 */
@ConditionalOnProperty(
        prefix = "controller.log",
        name = {"enabled"},
        havingValue = "true"
)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Configuration
public class RequestLogConfiguration {

    @Value("${controller.log.pointcut.expression:}")
    private String expression;

    @Bean
    public RequestLogInterceptor requestLogInterceptor() {
        return new RequestLogInterceptor();
    }


    @Bean
    public RequestLogPointcutAdvisor requestLogPointcutAdvisor(RequestLogInterceptor requestLogInterceptor) {
        RequestLogPointcutAdvisor advisor = new RequestLogPointcutAdvisor(this.expression);
        advisor.setAdviceBeanName("requestLogPointcutAdvisor");
        advisor.setAdvice(requestLogInterceptor);
        advisor.setOrder(Integer.MIN_VALUE);
        return advisor;
    }

}
