package io.github.eternalstone.common.toolkit.security.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * Created by eric on 2019/06/29.
 *
 * @author 老刘
 */
public class RSASignUtil {

    /**
     * 兼容公司目前签名场景,
     * 更加通用的方法 byte[] sign(SignParam param)
     * @param data
     * @param strPrivateKey
     * @return
     * @throws Exception
     */
    @Deprecated
    public static String sign(String data, String strPrivateKey) throws Exception {
        SignParam param = new SignParam();
        param.setSignatureAlgorithm("SHA256WithRSA");
        param.setCharset("utf-8");
        param.setData(data);
        PrivateKey privateKey = RSAKeyUtil.getPrivateKeyOfPKCS8(strPrivateKey);
        param.setKeyOfPrivate(privateKey);

        byte[] buffer = RSASignUtil.sign(param);
        String signed = Base64.encodeBase64String(buffer);
        return signed;
    }

    /**
     * @param param 签名参数类
     * @return
     */
    public static byte[] sign(SignParam param) {
        try {
            String charset = param.getCharset();
            PrivateKey keyOfPrivate = param.getKeyOfPrivate();
            if (keyOfPrivate == null) {
                if (StringUtils.isNotBlank(param.getPrivateKey())) {
                    keyOfPrivate = RSAKeyUtil.getPrivateKeyOfPKCS8(new ByteArrayInputStream(param.getPrivateKey().getBytes(charset)));
                } else {
                    throw new IllegalArgumentException("Private secretKey is null");
                }
            }
            Signature signature = Signature.getInstance(param.getSignatureAlgorithm());
            signature.initSign(keyOfPrivate);
            signature.update(param.getData().getBytes(charset));
            return signature.sign();
        } catch (Exception e) {
            throw new SignException("Signature error,", e);
        }
    }


    /**
     * 检查制定内容和 是否匹配 SIGN
     *
     * @param param 验签参数类
     * @return 是否通过
     */
    public static boolean verify(SignParam param) {
        try {
            String encoding = param.getCharset();
            PublicKey keyOfPublic = param.getKeyOfPublic();
            if (keyOfPublic == null) {
                if (StringUtils.isNotBlank(param.getPublicKey())) {
                    keyOfPublic = RSAKeyUtil.getPublicKeyFromX509(new ByteArrayInputStream(param.getPublicKey().getBytes(encoding)));
                } else {
                    throw new IllegalArgumentException("Public secretKey is null");
                }
            }
            Signature signature = Signature.getInstance(param.getSignatureAlgorithm());
            signature.initVerify(keyOfPublic);
            signature.update(param.getData().getBytes(encoding));
            return signature.verify(param.getSignedData());
        } catch (Exception e) {
            throw new SignException("Check signature error", e);
        }
    }
}
