package io.github.eternalstone.common.toolkit.util;

import io.github.eternalstone.common.toolkit.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 追踪日志组件
 * 1.生成追踪id(traceId)
 * 2.记录追踪日志
 *
 * @author c5game
 */
@Slf4j
public class TraceLogUtil {

    /**
     * 自增ID
     */
    private static AtomicLong lastId = new AtomicLong();
    /**
     * 本机IP
     */
    private static final String LOCAL_IP = getLocalHostIp();

    /**
     * ip段分隔符
     */
    private static final String IP_SEPARATOR_PATTERN = "\\.";
    /**
     * traceId分割符
     */
    private static final String TRACE_ID_SEPARATOR = "-";

    /**
     * 获取唯一的请求追踪ID
     *
     * @return String
     */
    public static String getTraceId() {

        return ipToHex(LOCAL_IP) + TRACE_ID_SEPARATOR + Long.toString(System.currentTimeMillis(), Character.MAX_RADIX) + TRACE_ID_SEPARATOR + lastId.incrementAndGet();
    }

    /**
     * 将IP转换成16进制数据
     *
     * @param ip
     * @return String
     */
    private static String ipToHex(String ip) {
        StringBuilder sb = new StringBuilder();
        for (String seg : ip.split(IP_SEPARATOR_PATTERN)) {
            String h = Integer.toHexString(Integer.parseInt(seg));
            if (h.length() == 1) {
                sb.append("0");
            }
            sb.append(h);
        }
        return sb.toString();
    }

    /**
     * 将16进制转化为IP
     *
     * @param hexString
     * @return
     */
    private static String hexToIp(String hexString) {
        int size = 2;
        int total = hexString.length();
        StringBuilder ipBuilder = new StringBuilder();
        for (int i = 0; i < total; i += size) {
            String part = hexString.substring(i, i + size);
            ipBuilder.append(Integer.valueOf(part, 16));
            if (i < 6) {
                ipBuilder.append(".");
            }
        }
        return ipBuilder.toString();
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    public static String getLocalHostIp() {
        String dockerSuffix = ".1";
        try {
            InetAddress candidateAddress = null;
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress() && !inetAddr.getHostAddress().endsWith(dockerSuffix)) {
                            return inetAddr.getHostAddress();
                        } else if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress.getHostAddress();
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress != null) {
                return jdkSuppliedAddress.getHostAddress();
            }
        } catch (Exception e) {
            log.error("未获取到IP地址");
        }
        return null;
    }

    /**
     * 解析traceId
     *
     * @param traceId
     * @return
     */
    public static Map<String, Object> parseTraceId(String traceId) {
        Map<String, Object> data = new HashMap<String, Object>(3);
        String[] traceArr = traceId.split(TRACE_ID_SEPARATOR);
        int normalLength = 3;
        if (traceArr.length == normalLength) {
            data.put("lastId", traceArr[2]);
            data.put("ip", hexToIp(traceArr[0]));
            data.put("time", Long.valueOf(traceArr[1], Character.MAX_RADIX));
        }

        return data;
    }

    /**
     * 获取唯一的请求追踪ID
     *
     * @return String
     */
    public static String getTraceId(String prefix) {
        return prefix + TRACE_ID_SEPARATOR + Long.toString(System.currentTimeMillis(), Character.MAX_RADIX) + TRACE_ID_SEPARATOR + lastId.incrementAndGet();
    }

    private static final String UNKNOWN = "UNKNOWN";
    private static final String UNKNOWN_TYPE = "UNKNOWN_TYPE";
    private static final String OK = "OK";

    public static void traceLog(TraceLog traceLog) {
        if (traceLog != null) {
            log.info("{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}",
                    traceLog.traceId == null ? UNKNOWN : traceLog.traceId,
                    StringUtils.isBlank(traceLog.clientName) ? "UNKNOWN" : traceLog.clientName,
                    StringUtils.isBlank(traceLog.serverAppName) ? "UNKNOWN" : traceLog.serverAppName,
                    StringUtils.isBlank(traceLog.type) ? UNKNOWN_TYPE : traceLog.type,
                    traceLog.service == null ? "" : traceLog.service,
                    traceLog.method == null ? "" : traceLog.method,
                    traceLog.clientAddress == null ? "" : traceLog.clientAddress,
                    traceLog.clientPort,
                    traceLog.serverAddress == null ? "127.0.0.1" : traceLog.serverAddress,
                    traceLog.serverPort,
                    traceLog.begin,
                    traceLog.end == 0L ? System.currentTimeMillis() - traceLog.begin : traceLog.end - traceLog.begin,
                    traceLog.code,
                    traceLog.result == null && traceLog.code == 200 ? OK : traceLog.result);
        }
    }

    public static String getRootMessage(Throwable e) {
        if (e instanceof BizException) {
            return e.getMessage();
        } else {
            Throwable root = getRootCause(e);
            return root == null ? "OK" : root.getClass().getSimpleName();
        }
    }

    public static Throwable getRootCause(Throwable e) {
        if (e == null) {
            return null;
        } else {
            return e.getCause() != null && e.getCause() != e ? getRootCause(e.getCause()) : e;
        }
    }

    public static final class TraceLog {
        final String traceId;
        final String clientName;
        final String serverAppName;
        final String type;
        final String service;
        final String method;
        final String clientAddress;
        final int clientPort;
        final String serverAddress;
        final int serverPort;
        final long begin;
        final long end;
        final int code;
        final String result;

        public TraceLog(String traceId, String clientName, String serverAppName, String type, String service, String method, String clientAddress, int clientPort, String serverAddress, int serverPort, long begin, long end, int code, String result) {
            this.traceId = traceId;
            this.clientName = clientName;
            this.serverAppName = serverAppName;
            this.type = type;
            this.service = service;
            this.method = method;
            this.clientAddress = clientAddress;
            this.clientPort = clientPort;
            this.serverAddress = serverAddress;
            this.serverPort = serverPort;
            this.begin = begin;
            this.end = end;
            this.code = code;
            this.result = result;
        }
    }

}
