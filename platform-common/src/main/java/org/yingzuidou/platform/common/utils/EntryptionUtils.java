package org.yingzuidou.platform.common.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * 类功能描述
 * <p>加密工具，提供多种加密方式，如果新增一种加密方式则在新的内部类中实现
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class EntryptionUtils {


    public static String aesEncryption(String raw) {
        return AesEncryptUtils.encrypt(raw);
    }

    public static String aesDecryption(String encryt) {
        return AesEncryptUtils.decrypt(encryt);
    }

    private static class AesEncryptUtils {

        /**
         * 可配置到Constant中，并读取配置文件注入,16位,自己定义
         */
        private static final String KEY = "#*S*X*3883838x88";

        /**
         * 参数分别代表 算法名称/加密模式/数据填充方式
         */
        private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

        /**
         * AES加密方式
         *
         * @param content    加密的字符串
         * @param encryptKey key值
         */
        public static String encrypt(String content, String encryptKey){
            byte[] b;
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128);
                Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
                b = cipher.doFinal(content.getBytes("utf-8"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 采用base64算法进行转码,避免出现中文乱码
            return Base64.encodeBase64String(b);

        }

        /**
         * AES解密
         *
         * @param encryptStr 解密的字符串
         * @param decryptKey 解密的key值
         * @return
         */
        public static String decrypt(String encryptStr, String decryptKey) {
            byte[] decryptBytes;
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128);
                Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
                // 采用base64算法进行转码,避免出现中文乱码
                byte[] encryptBytes = Base64.decodeBase64(encryptStr);
                decryptBytes = cipher.doFinal(encryptBytes);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }

            return new String(decryptBytes);
        }

        public static String encrypt(String content){
            return encrypt(content, KEY);
        }

        public static String decrypt(String encryptStr) {
            return decrypt(encryptStr, KEY);
        }

    }
}
