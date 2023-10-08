package io.github.eternalstone.common.toolkit.web.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 16:30
 */
public class TraceIdUtil {

    private static final String TRACE_ID = "trace_id";
    private static final String TRACE_ID_HUMP = "traceId";

    public TraceIdUtil() {
    }

    public static String getTraceIdHttpRequest(HttpServletRequest request) {
        String traceId = StringUtils.isBlank(request.getHeader(TRACE_ID)) ? request.getHeader(TRACE_ID_HUMP) : request.getHeader(TRACE_ID);
        if (StringUtils.isNotBlank(traceId)) {
            return traceId;
        } else {
            return getTraceId();
        }
    }

    public static String getTraceId() {
        return UUID.randomUUID().toString();
    }
}
