package io.github.eternalstone.common.toolkit.web.log;

import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 16:43
 */
public class RequestLogPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private String expression;

    public RequestLogPointcutAdvisor(String expression) {
        this.expression = expression;
    }

    public Pointcut aspectJExpressionPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(this.expression);
        return pointcut;
    }

    public Pointcut getPointcut() {
        return this.aspectJExpressionPointcut();
    }
}
