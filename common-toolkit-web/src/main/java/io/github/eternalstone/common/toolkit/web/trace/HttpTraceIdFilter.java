package io.github.eternalstone.common.toolkit.web.trace;

import io.github.eternalstone.common.toolkit.web.util.TraceIdUtil;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 16:29
 */
@WebFilter(filterName = "httpTraceIdFilter")
public class HttpTraceIdFilter implements Filter {

    public HttpTraceIdFilter() {
    }

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String traceId = "";
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
            traceId = TraceIdUtil.getTraceIdHttpRequest(httpServletRequest);
        }

        MDC.put("traceId", traceId);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }

    }

    public void destroy() {
    }

}
