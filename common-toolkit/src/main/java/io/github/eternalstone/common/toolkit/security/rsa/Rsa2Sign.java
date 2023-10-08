package io.github.eternalstone.common.toolkit.security.rsa;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class Rsa2Sign {
    public static String sign(String content, String privateKey) {
        try {
            PrivateKey privateKey_ = getPrivateKey(privateKey);
            //转成byte数组，按实际情况使用字符集，此处使用utf-8
            byte[] reqBs = content.getBytes("UTF-8");
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(privateKey_);
            signature.update(reqBs);
            byte[] signedBs = signature.sign();
            //对签名数据进行base64编码,并对一些特殊字符进行置换
            String signedStr = Base64.encodeBase64String(signedBs);
            //System.out.println(signedStr);
            //将签名信息加入原始请求报文map
            return signedStr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把私钥转换为PrivateKey对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

}
