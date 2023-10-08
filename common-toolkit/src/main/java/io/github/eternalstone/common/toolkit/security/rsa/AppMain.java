package io.github.eternalstone.common.toolkit.security.rsa;

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RES签名和验证签名app main
 * Created by eric on 2019/6/29.
 *
 * @author 老刘
 */
public class AppMain {
    public static void main(String[] args) {

        PrivateKey privateKey = null;
        PublicKey publicKey = null;
        String strPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtftuGagiPIXRTivRnx1K9XcgFIBYyQkGvGACkCZNMmIiDkto9QNAJPsUgk3J5KKTEiI3Va31spzfFdH5plDkSRRJjsi65heT239hCC+h+4uv7x1ClKQ8b/0xuO6rM/E+4f2kUCoflYiOxE2NO5gzROI4DSXESahApGN2CqPFv7WGz3XRqwvgZk1dpW24T+c1Rxc3Z4o2VmVlhieUXOL1i3+IWwjuu/m4mDT0rMBmA2IVLClXLgqzjIVypn09qRCDACq01WA/1jUai4EuSpjXSEzaLhUkcc2GhjeJR4glMdV6DHOgzeiI9UjqB5tCH6rG4K11PPBT27F2aNLQj3h50wIDAQAB";
        String strPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1+24ZqCI8hdFOK9GfHUr1dyAUgFjJCQa8YAKQJk0yYiIOS2j1A0Ak+xSCTcnkopMSIjdVrfWynN8V0fmmUORJFEmOyLrmF5Pbf2EIL6H7i6/vHUKUpDxv/TG47qsz8T7h/aRQKh+ViI7ETY07mDNE4jgNJcRJqECkY3YKo8W/tYbPddGrC+BmTV2lbbhP5zVHFzdnijZWZWWGJ5Rc4vWLf4hbCO67+biYNPSswGYDYhUsKVcuCrOMhXKmfT2pEIMAKrTVYD/WNRqLgS5KmNdITNouFSRxzYaGN4lHiCUx1XoMc6DN6Ij1SOoHm0IfqsbgrXU88FPbsXZo0tCPeHnTAgMBAAECggEAee/cKTLgpd8gjRmNXulY1bsZcsNBOotiyQwoC4Cuj0jz+tIKpMmlzGG0r08CLcHFfAwTOcTTcffs/wjwF8HyBQmj9BmKKiy4NQZX9Q09o3hIzZa0iza1DHECRSEe9DH86Z5z4AcpnJ+g0eYI8VqdV9WgqyaYLKahkcTXTm/ITBhrwCX9v4mEMPj7651YSZBXMkTMxmIrwuEcVFtzI5v2HpFFlPxzcdkPFUx2FTtkEteN7dp2wbBL/XD9wLIyVoVzoYWaR+mI2lsOZQocodr0iGZaVzvqG1F2JKFc3h6WBUxfv/iE83I6luBtWERTmATf3ZKY220TpwTJkGvhlaPbwQKBgQDddVTKpKawwj7NgZqBS1426bl1BhBfErMG59f4NfabTldDfa66FueSBjl1lQx1MgSGl5VDJWi+GR/beUbIkBG88Kb3zjf4aw3+uHKc6d5yVkdy2vjjiZX1+B25kP0lO+1tRPdyJ3YWyiTYQoLBdwv29zGJG9/XyN4nJdeD+MzZ4QKBgQDSXdgTN7gInqhVelkZv0296eoq5ooTZcinxc78eFudM6EYPJqRpnYVfLNnb/q6pBgXazPj7RNHTqb8sO9vEkTaCgcJRw1RZNJ97prrh1W4BETNnvcNXAVZx1KCGuwNUS4NgwN6gyuh3dg9uMwEgdah3cxzk5PnQI6S7oMsEr9SMwKBgQC3QdiguA+YRaViudT5GT0lg9OMGzCA/CBZnpEBPOaNLg7YMrDcHQgTtkLmIijk8jb8iYNixRof9Pp/y6PfZBjJmPsSJPv7/dre5hMx9fW199+4E6vR3pDRsjgmzuwsW6LlwSRiunTl9vD2ZFN/dNvZykSbiQ16qyXqj04AT2p1AQKBgGce9ZLGF4KyM5ZjOVYzvuo/xjhtdnX3yRrlDogYf+BZtLhqzOJlFTGHRXKJJAFl+yFPvcoXJwInfhgUq+porDOuArD7kX26zKxK0MpwNwbToN6i9DwqRb0yxjFc/SWyC7833/aoYjUgyKXI9smm1A3Y6iYly7TqonlITijGr96dAoGBALzyWjJUSWOLvKQBLb7Cbys9gK/FohJt7buYwj7/5lbqwXwbQYlsIbdeETP/A+9q+lvoOj0jPUEoeLe1rxw8CZMPWGV2yjMeA6RdjL4efkbW0LZgrOW8Oj9h/vwHHBD4Ir1VTY3I+BUDpc2DN9vWeoezrqpnG+ciX2oC9tT5nE7f";

        String data = "I am CommonToolkit";
        try {
            privateKey = RSAKeyUtil.getPrivateKeyOfPKCS8(strPrivateKey);
            publicKey = RSAKeyUtil.getPublicKeyFromX509(strPublicKey);

            SignParam param = new SignParam();
            param.setSignatureAlgorithm("SHA256WithRSA");
            param.setCharset("utf-8");
            param.setData(data);

            param.setKeyOfPrivate(privateKey);
            byte[] buffer = RSASignUtil.sign(param);
            String signed = Base64.encodeBase64String(buffer);

            // Check-sign
            System.out.println(signed);
            byte[] checkSignbuffer = Base64.decodeBase64(signed.getBytes("utf-8"));
            param.setSignedData(checkSignbuffer);
            param.setKeyOfPublic(publicKey);
            boolean result = RSASignUtil.verify(param);
            System.out.println("验证签名结果:" + result);


            String signedV2 = Rsa2Sign.sign(data, strPrivateKey);
            if (signed.equalsIgnoreCase(signedV2)) {
                System.out.println("Sign is the same");
            }

            String signedV3 = RSASignUtil.sign(data, strPrivateKey);
            if (signedV3.equalsIgnoreCase(signedV2)) {
                System.out.println("Signature(v2==v3) is the same");
            }

            String publicExtent = "d5924be2651c0f60740051140f8d3f1293f3601a24e511867b5f0384020501466cd86221a2de55e2e34ab628f9810d8f9e6e2ced3c8fe3ac9e67a28475593eb9408aa2033633c9c90155af94b0fd7636e563a4f87657f5eb5cf0356eed73c2ce85c45eea17e64118226bfb78147667b6f0388fae92449b5a385f87b953d41084f0af096d18982cb303da3b724b0e0307b58690c97b12f10c218720e71eb8bb30ef40518b7472a1ffaf6d4b7a7188e5b35fcf4813009d7ba8bd231fddb4cf9334edde2443944374706dcf87d59cc2c02825c1588c288b723b83219d734d289fb0360e79c5783c3af52f8a31f056e7eb76f8ee7e91819a7e1c04dd36c1a80f804b";
            boolean publicExtendBase64 = Base64.isBase64(publicExtent);
            if(publicExtendBase64){
               byte[] binPC = Base64.decodeBase64(publicExtent);
                BigInteger bigIntPrivateExponent = new BigInteger(1, binPC);
                System.out.println(bigIntPrivateExponent);
            }
            System.out.println(publicExtendBase64);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
