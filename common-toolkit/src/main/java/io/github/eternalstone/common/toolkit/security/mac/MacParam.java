package io.github.eternalstone.common.toolkit.security.mac;

import lombok.Data;

@Data
public class MacParam {
    /**
     * 签名数据是否需要排序(默认情况下是升序)
     */
    private boolean needOrderParamMap;
    /**
     * 摘要算法(eg,HmacMD5、HmacSHA1、HmacSHA256、HmacSHA384、HmacSHA512,default:HmacSHA256)
     */
    private MacAlgorithm algorithm;
    /**
     * 签名结果是否以十六进制字符串显示
     */
    private boolean needHex;
    private boolean needBase64;
    /**
     * 字符(default charset:UTF-8)
     */
    private String charset = "UTF-8";
    /**
     * 秘钥,生成MAC需要的秘钥(必须项)
     */
    private String secretKey;
}
