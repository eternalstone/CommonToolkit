package io.github.eternalstone.common.toolkit.security.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by eric on 2019/06/29.
 *
 * @author 老刘
 */
public class RSAKeyUtil {

    final static String RSA = "RSA";

    public static PrivateKey getPrivateKeyOfPKCS8(String privateKey) throws Exception {
        byte[] content = null;
        if (Base64.isBase64(privateKey)) {
            content = Base64.decodeBase64(privateKey);
        }
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PKCS8EncodedKeySpec ps = new PKCS8EncodedKeySpec(content);
        return keyFactory.generatePrivate(ps);
    }

    public static PublicKey getPublicKeyFromX509(String publicKey) throws Exception {
        byte[] content = null;
        if (Base64.isBase64(publicKey)) {
            content = Base64.decodeBase64(publicKey);
        }
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new X509EncodedKeySpec(content));
    }

    public static PrivateKey getPrivateKeyOfPKCS8(InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        byte[] encodedKey = IOUtils.toByteArray(ins);
        encodedKey = Base64.decodeBase64(encodedKey);
        PKCS8EncodedKeySpec ps = new PKCS8EncodedKeySpec(encodedKey);
        return keyFactory.generatePrivate(ps);
    }

    public static PublicKey getPublicKeyFromX509(InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        byte[] encodedKey = IOUtils.toByteArray(ins);
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }
}
