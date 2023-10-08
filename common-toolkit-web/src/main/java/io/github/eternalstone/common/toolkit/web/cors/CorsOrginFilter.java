package io.github.eternalstone.common.toolkit.web.cors;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 16:56
 */
public class CorsOrginFilter implements Filter {

    private CorsOriginProperties properties;

    public CorsOrginFilter(CorsOriginProperties properties) {
        this.properties = properties;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String origin = req.getHeader("Origin");
        if (CollectionUtils.isEmpty(properties.getOrigins())) {
            resp.setHeader("Access-Control-Allow-Origin", CorsConfiguration.ALL);
        } else if (properties.getOrigins().contains(origin)) {
            resp.setHeader("Access-Control-Allow-Origin", origin);
        }
        resp.setHeader("Access-Control-Allow-Methods", properties.getAllowedMethods());
        resp.setHeader("Access-Control-Allow-Headers", properties.getAllowedHeaders());
        resp.setHeader("Access-Control-Max-Age", String.valueOf(properties.getMaxAge()));
        resp.setHeader("Access-Control-Allow-Credentials", BooleanUtils.toStringTrueFalse(properties.isCredentials()));
        if (req.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
        // 如果是其它请求方法，则继续过滤器。
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
