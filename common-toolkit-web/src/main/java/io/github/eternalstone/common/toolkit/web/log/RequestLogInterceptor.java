package io.github.eternalstone.common.toolkit.web.log;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.eternalstone.common.toolkit.threadlocal.LocalFiled;
import io.github.eternalstone.common.toolkit.threadlocal.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 16:41
 */
@Slf4j
public class RequestLogInterceptor implements MethodInterceptor {

    @Value("${controller.log.level:NONE}")
    private String level;
    @Value("${controller.log.warn.rt:2000}")
    private int warningResponseTimeMs;
    private final ObjectWriter objectWriter = new ObjectMapper().writer();

    public RequestLogInterceptor() {

    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        HttpLogLevel logHttpLogLevel = HttpLogLevel.valueOf(this.level);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            //没有命中切点，直接放行
            return invocation.proceed();
        }
        HttpServletRequest request = requestAttributes.getRequest();
        StringBuilder sb = new StringBuilder();
        Object ret = invocation.proceed();
        long rt = System.currentTimeMillis() - startTime;
        if (rt > (long) this.warningResponseTimeMs) {
            log.warn("RT reach the limit,ClassMethod: {}.{} rt: {} ms", new Object[]{invocation.getMethod().getDeclaringClass().getName(), invocation.getMethod().getName(), rt});
        }

        if (logHttpLogLevel.ordinal() >= HttpLogLevel.BASIC.ordinal()) {
            sb.append(String.format("ClassMethod: %s.%s", invocation.getMethod().getDeclaringClass().getName(), invocation.getMethod().getName()));
            sb.append("\n");
            sb.append(String.format("http request: [%s -> %s]", request.getMethod(), request.getRequestURI()));
            if (!StringUtils.isEmpty(request.getQueryString())) {
                sb.append(String.format(", queryString: %s", request.getQueryString()));
            }

            sb.append("\n");
            if (logHttpLogLevel.ordinal() >= HttpLogLevel.HEADERS.ordinal()) {
                LocalFiled localFiled = ThreadLocalUtil.getLocalFiled();
                sb.append(localFiled != null ? String.format("RequestThreadLocal: %s", localFiled.toString()) : "");
                sb.append("\n");
                if (invocation.getArguments().length != 0) {
                    List<Object> argsList = (List) Arrays.stream(invocation.getArguments()).filter((arg) -> {
                        return !(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse) && !(arg instanceof MultipartFile);
                    }).collect(Collectors.toList());
                    sb.append(this.objectWriter.writeValueAsString(argsList));
                }

                sb.append("\n");
                if (logHttpLogLevel.ordinal() >= HttpLogLevel.FULL.ordinal()) {
                    sb.append(String.format("response: %s", this.objectWriter.writeValueAsString(ret)));
                }
            }

            log.info(sb.toString());
        }

        return ret;
    }
}
