package io.github.eternalstone.common.toolkit.security.aes;

import lombok.Data;

@Data
public class AesParam {
    /**
     * Message digest charset(default charset:UTF-8)
     */
    private String charset = "UTF-8";
    /**
     * The secretKey of encrypt/decrypt
     */
    private String key = "y78YTPgbxonRRdov";
    /**
     * 算法/模式/补码方式 eg,AES/CBC/NoPadding";(默认值:AES/CBC/PKCS5Padding)
     */
    private String CipherInstanceArg = "AES/CBC/PKCS5Padding";

    /**
     * The iv for IvParameterSpec
     */
    private String iv = "rak5aXQRvvvu3dtL";

    /**
     * Wrong keysize: must be equal to 128, 192 or 256(default:128)
     */
    private int keysize = 128;
    /**
     * 是否开启 SecureRandom(默认情况下不开启)
     */
    private boolean usingSecureRandom = false;
}
