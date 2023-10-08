package io.github.eternalstone.common.toolkit.security.mac;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MacUtil {

    /**
     * 加密
     *
     * @param algorithm
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(MacAlgorithm algorithm, byte[] data, byte[] key) throws MacException {
        return encrypt(algorithm.getName(), data, key);
    }

    /**
     * 加密
     *
     * @param algorithm
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(String algorithm, byte[] data, byte[] key) throws MacException {
        try {
            return getMac(algorithm, key).doFinal(data);
        } catch (Exception ex) {
            throw new MacException("Mac encrypt error", ex);
        }
    }

    /**
     * 加密
     *
     * @param algorithm
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String algorithm, String data, String key) throws MacException {
        try {
            byte[] binData = data.getBytes("UTF-8");
            byte[] binKey = key.getBytes("UTF-8");
            byte[] binEncrypt = getMac(algorithm, binKey).doFinal(binData);
            return Base64.encodeBase64String(binEncrypt);
        } catch (Exception ex) {
            throw new MacException("Mac encrypt error", ex);
        }
    }


    /**
     * 初始化密钥
     *
     * @param algorithm
     * @return
     */
    public static byte[] initKey(MacAlgorithm algorithm) throws MacException {
        return initKey(algorithm.getName());
    }

    /**
     * 初始化密钥
     *
     * @param algorithm
     * @return
     */
    public static byte[] initKey(String algorithm) throws MacException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new MacException("No Such Algorithm Exception", ex);
        }
    }

    private static Mac getMac(String algorithm, byte[] key) throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        if (key != null) {
            try {
                SecretKey secretKey = new SecretKeySpec(key, algorithm);
                mac.init(secretKey);
            } catch (InvalidKeyException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return mac;
    }
}
