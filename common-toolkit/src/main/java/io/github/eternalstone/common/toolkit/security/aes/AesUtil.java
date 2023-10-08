package io.github.eternalstone.common.toolkit.security.aes;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;


/**
 * AES 加密&解密工具类
 */
public class AesUtil {
    private final static String AES = "AES";
    private static String ALGO_MODE = "AES/CBC/NoPadding";
    private static String IV = "rak5aXQRvvvu3dtL";
    private static String KEY = "y78YTPgbxonRRdov";
    private static final String CHARSET = "UTF-8";
    /**
     * Description: AES解密
     */
    public static String decrypt(String sSrc, String sKey, String siv) throws Exception {
        try {
            if (sSrc == null || sSrc.length() == 0) {
                return null;
            }
            if (sKey == null) {
                throw new Exception("decrypt key is null");
            }
            if (sKey.length() != 16) {
                throw new Exception("decrypt key length error");
            }
            byte[] Decrypt = ByteFormat.hexToBytes(sSrc);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(CHARSET), "AES");
            IvParameterSpec iv = new IvParameterSpec(siv.getBytes(CHARSET));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            return new String(cipher.doFinal(Decrypt), CHARSET);
        } catch (Exception ex) {
            throw new Exception("decrypt errot", ex);
        }
    }
    /**
     * 这种加密方式,仅用于公司目前的场景
     * 更加通用的方式是 encrypt(String data, AesParam param)
     *
     * @description encrypt data by default param
     * @author qichengjian
     * @date 2019/6/29
     */
    @Deprecated
    public static String encrypt(String data) throws AesException {
        AesParam param = new AesParam();
        return encrypt(data, param);
    }

    /**
     * 这种解密方式,仅用于公司目前的场景
     * 更加通用的方式是 decrypt(String encryptedData, AesParam param)
     *
     * @description decrypt data by default param
     * @author qichengjian
     * @date 2019/6/29
     */
    @Deprecated
    public static String decrypt(String encryptedData) {
        AesParam param = new AesParam();
        return decrypt(encryptedData, param);
    }

    /**
     * 用来进行加密的操作
     *
     * @param data
     * @param param
     * @return
     * @throws AesException
     */
    public static String encrypt(String data, AesParam param) throws AesException {
        if (StringUtils.isBlank(data)) {
            throw new AesException("Encrypt data is null");
        }
        if (param == null) {
            throw new AesException("Aes Parameter class is null");
        }

        // 参数检查
        String instanceArg = param.getCipherInstanceArg();
        String key = param.getKey();
        if (StringUtils.isBlank(instanceArg) || StringUtils.isBlank(key)) {
            throw new AesException("Aes Parameter is null");
        }

        String paramIv = param.getIv();
        String charset = param.getCharset();
        boolean usingSecureRandom = param.isUsingSecureRandom();

        try {
            KeyGenerator generator = KeyGenerator.getInstance(AES);
            if (usingSecureRandom) {
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(key.getBytes(charset));
                generator.init(param.getKeysize(), random);
            }

            byte[] binKey = key.getBytes(charset);
            SecretKeySpec secretKeySpec = new SecretKeySpec(binKey, AES);

            IvParameterSpec ivParameterSpec = new IvParameterSpec(paramIv.getBytes(charset));
            Cipher cipher = Cipher.getInstance(instanceArg);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            // 加密前
            byte[] binPreEncrypt = data.getBytes(charset);
            // 加密后
            byte[] encrypted = cipher.doFinal(binPreEncrypt);
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            throw new AesException("AES encrypt error", e);
        }
    }

    /**
     * 用来进行解密的操作
     *
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptedData, AesParam param) {
        if (StringUtils.isBlank(encryptedData)) {
            throw new AesException("Encrypt data is null");
        }
        if (param == null) {
            throw new AesException("Aes Parameter class is null");
        }

        String instanceArg = param.getCipherInstanceArg();
        String key = param.getKey();
        if (StringUtils.isBlank(instanceArg) || StringUtils.isBlank(key)) {
            throw new AesException("Aes Parameter is null");
        }

        String paramIv = param.getIv();
        int keySize = param.getKeysize();
        String charset = param.getCharset();
        boolean usingSecureRandom = param.isUsingSecureRandom();

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            if (usingSecureRandom) {
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(key.getBytes(charset));
                keyGenerator.init(keySize, random);
            }

            byte[] binKey = key.getBytes(charset);
            SecretKeySpec secretKeySpec = new SecretKeySpec(binKey, AES);

            Cipher cipher = Cipher.getInstance(instanceArg);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(paramIv.getBytes(charset));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = Base64.decodeBase64(encryptedData);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, Charset.forName(charset));
        } catch (Exception e) {
            throw new AesException("AES decrypt error", e);
        }
    }
}
