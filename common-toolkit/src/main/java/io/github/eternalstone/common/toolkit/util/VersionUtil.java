package io.github.eternalstone.common.toolkit.util;

import org.apache.commons.lang3.StringUtils;

/**
 * to do something
 *
 * @author Justzone on 2021/11/19 17:19
 */
public class VersionUtil {


    /**
     * 比较版本号
     * 返回当前版本是否是最新版本
     */
    public static boolean compareVersion(String clientVersion, String maxVersion) {
        //如果服务端没有最高版本号，直接返回true
        if (StringUtils.isBlank(maxVersion)) {
            return true;
        }
        String[] currentVersion = clientVersion.split("\\.");
        String[] lastVersion = maxVersion.split("\\.");
        int length = Math.min(currentVersion.length, lastVersion.length);
        int diff = 0;
        for (int i = 0; i < length; i++) {
            String v1 = currentVersion[i];
            String v2 = lastVersion[i];
            diff = v1.length() - v2.length();
            if (diff == 0) {
                diff = v1.compareTo(v2);
            }
            if (diff != 0) {
                break;
            }
        }
        diff = (diff != 0) ? diff : (currentVersion.length - lastVersion.length);
        return diff >= 0;
    }

}
