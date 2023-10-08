package io.github.eternalstone.common.toolkit.web.xss;

import io.github.eternalstone.common.toolkit.web.util.SpringUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 17:18
 */
public class XssInterceptor implements AsyncHandlerInterceptor {

    private XssProperties xssProperties;

    public XssInterceptor(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 非控制器请求直接跳出
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 2. 没有开启
        if (!xssProperties.isEnabled()) {
            return true;
        }
        // 3. 处理 XssIgnore 注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        XssIgnore xssIgnore = SpringUtil.getAnnotation(handlerMethod, XssIgnore.class);
        if (xssIgnore != null) {
            XssHolder.setIgnore(xssIgnore);
        }
        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        XssHolder.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        XssHolder.remove();
    }
}
