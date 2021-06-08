package org.greenleaf.java;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 功能简述: 加密解密工具类，对MD5/BASE64/DES/RSA等算法提供了包装.
 * @author Nick Xu
 * @version 1.0
 */
public class EncryUtils {

    private static Logger logger = Logger.getLogger(EncryUtils.class.getName());

    private static final int KEY_SIZE = 1024;
    private static final String  MD5_ALGORITHM= "md5";
    private static final String  DES_ALGORITHM= "des";
    private static final String  RSA_ALGORITHM= "rsa";
    private static final String  SIGNATURE_ALGORITHM= "MD5withRSA";

    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final SecureRandom random = new SecureRandom();
    private static KeyPair keyPair;

    private EncryUtils() {
    }

    /**
     *  功能简述: 使用md5进行单向加密.
     *  单向加密算法，只能加密、无法解密。
     *  已经被成功破译
     *  在安全性要求不高的场景下，仍然具有应用价值
     */
    public static String md5(String plainText) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(MD5_ALGORITHM);
        byte[] cipherData = md5.digest(plainText.getBytes());
        return toHexString(cipherData);
    }

    /**
     * 功能简述: 使用BASE64进行加密.
     * BASE64算法通常用作对二进制数据进行加密
     * 严格来说，经过BASE64加密的数据其实没有安全性可言保密，因为它的加密解密算法都是公开的
     * @param plainData 明文数据
     * @return 加密之后的文本内容
     */
    public static String encodeBase64(byte[] plainData) {
        return encoder.encodeToString(plainData);
    }

    /**
     * 功能简述: 使用BASE64进行解密.
     * @param    cipherText 密文文本
     * @return   解密之后的数据
     */
    public static byte[] decodeBase64(String cipherText) {
        return decoder.decode(cipherText);
    }

    /**
     *  功能简述: 使用DES算法进行加密.数据加密标准算法
     *  @param plainData 明文数据
     *  @param key   加密密钥
     *  @return
     */
    public static byte[] encryptDES(byte[] plainData, String key) {
        return processCipher(plainData, createDESSecretKey(key), Cipher.ENCRYPT_MODE, DES_ALGORITHM);
    }

    /**
     * 功能简述: 使用DES算法进行解密.
     * @param cipherData    密文数据
     * @param key   解密密钥
     * @return
     */
    public static byte[] decryptDES(byte[] cipherData, String key) {
        return processCipher(cipherData, createDESSecretKey(key), Cipher.DECRYPT_MODE, DES_ALGORITHM);
    }

    /**
     * 功能简述: 根据key创建密钥SecretKey.
     * @param key
     * @return
     */
    private static SecretKey createDESSecretKey(String key) {
        SecretKey secretKey = null;
        try {
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            secretKey = keyFactory.generateSecret(keySpec);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }
        return secretKey;
    }

    /**
     * 功能简述: 加密/解密处理流程.
     * @param processData   待处理的数据
     * @param key   提供的密钥
     * @param opsMode   工作模式
     * @param algorithm   使用的算法
     * @return
     */
    private static byte[] processCipher(byte[] processData, Key key, int opsMode, String algorithm) {
        try{
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(opsMode, key, random);
            return cipher.doFinal(processData);
        }
        catch (Exception e) {
            // Exception handler
            logger.log(Level.SEVERE, "", e);
        }
        return null;
    }

    /**
     * 功能简述: 创建RSA非对称加密KeyPair.
     * @return
     */
    public static KeyPair createRsaKeypair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 功能简述: 使用RSA算法加密.
     * @param plainData 明文数据
     * @param key   密钥
     * @return
     */
    public static byte[] encryptRSA(byte[] plainData, Key key) {
        return processCipher(plainData, key, Cipher.ENCRYPT_MODE, RSA_ALGORITHM);
    }

    /**
     * 功能简述: 使用RSA算法解密.
     * @param cipherData    密文数据
     * @param key   密钥
     * @return
     */
    public static byte[] decryptRSA(byte[] cipherData, Key key) {
        return processCipher(cipherData, key, Cipher.DECRYPT_MODE, RSA_ALGORITHM);
    }

    /**
     * 功能简述: 使用私钥对加密数据创建数字签名.
     * @param cipherData 已经加密过的数据
     * @param privateKey 私钥
     * @return
     */
    public static byte[] createRSASignature(byte[] cipherData, PrivateKey privateKey) {
        try {
            Signature signature  = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(cipherData);
            return signature.sign();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }
        return null;
    }

    /**
     * 功能简述: 使用私钥对加密数据创建数字签名.
     * @param cipherData 已经加密过的数据
     * @param privateKey 私钥
     * @return
     */
    public static String createRSASignatureHex(byte[] cipherData, PrivateKey privateKey) {
        try {
            Signature signature  = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(cipherData);
            return toHexString(signature.sign());
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }
        return null;
    }

    /**
     * 功能简述: 使用公钥对数字签名进行验证.
     * @param signData  数字签名
     * @param publicKey 公钥
     * @return
     */
    public static boolean verifySignature(byte[] cipherData, byte[] signData, PublicKey publicKey) {
        try {
            Signature signature  = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(cipherData);
            return signature.verify(signData);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }
        return false;
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte b1 : b) {
            sb.append(HEX_DIGITS[(b1 & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b1 & 0x0f]);
        }
        return sb.toString();
    }
}