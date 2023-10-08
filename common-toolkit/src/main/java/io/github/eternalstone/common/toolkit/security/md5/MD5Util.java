package io.github.eternalstone.common.toolkit.security.md5;

import java.security.MessageDigest;

/**
 * @author 老刘
 */
public class MD5Util {
    /**
     * 生成摘要
     *
     * @param text
     * @return
     */
    public static String digist(String text) {
        return digist(text, "UTF-8");
    }

    /**
     * 生成摘要
     *
     * @param text
     * @return
     */
    public static String digist(final String text, final String charsetname) {
        try {
            return digist(text.getBytes(charsetname));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Unsupported Encoding Exception",ex);
        }
    }

    /**
     * 生成摘要
     *
     * @param data
     * @return
     */
    public static String digist(byte[] data) {
        String result = "";

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(data);
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            //ignore ex
        }
        return result;
    }
}
