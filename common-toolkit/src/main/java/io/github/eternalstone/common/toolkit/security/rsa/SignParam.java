package io.github.eternalstone.common.toolkit.security.rsa;

import lombok.Data;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 签名和验签参数类
 */
@Data
public class SignParam {
    /**
     * 签名/验证算法(默认:SHA1WithRSA)
     */
    private String signatureAlgorithm = "SHA1WithRSA";
    /**
     * 签名数据
     */
    private String data;
    /**
     * 已数字签名数据
     */
    private byte[] signedData;
    /**
     * 私钥(字符串)
     */
    private String privateKey;
    /**
     * 私钥
     */
    private PrivateKey keyOfPrivate;
    /**
     * 公钥(字符串)
     */
    private String publicKey;
    /**
     * 公钥
     */
    private PublicKey keyOfPublic;
    /**
     * 签名验证数据编码(默认:UTF-8)
     */
    private String charset = "UTF-8";
    private InputStream streamOfPrivate;
    private InputStream streamOfPublic;
    private String keyFileFormat;
}
