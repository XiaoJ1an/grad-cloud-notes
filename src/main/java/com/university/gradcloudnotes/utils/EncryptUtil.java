package com.university.gradcloudnotes.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**加密信息类*/
@UtilityClass
public class EncryptUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    private static final String ALGORITHM = "DESede";

    /**返回加密字符串*/
    public static String getEncrypeStr(String key, String passwd) {
        /**加密报文*/
        byte[] bytes = encryStr(key.getBytes(), passwd.getBytes());
        return bytes == null ? null : toSting(bytes);
    }

    private static byte[] encryStr(byte[] keyByte, byte[] src) {
        try {
            /**生成密钥*/
            SecretKeySpec spec = new SecretKeySpec(keyByte, ALGORITHM);
            /**加密*/
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, spec);
            return cipher.doFinal(src);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
            logger.info("文件加密初始化失败！e1={}", e1);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e2) {
            logger.info("文件加密失败！e2={}", e2);
        }
        return null;
    }

    /**2进制转16进制*/
    private static String toSting(byte buffer[]) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (int pos = 0; pos < buffer.length; pos++) {
                stringBuilder.append(hexToAscii((buffer[pos] >>> 4) & 0x0F)).append(hexToAscii(buffer[pos] & 0x0F));
            }
        } catch (Exception e) {
            logger.info("2进制转16进制失败！！！e={}", e);
            throw new RuntimeException("加密异常，请检查数据格式！！！");
        }
        return stringBuilder.toString();
    }

    /**将hex转换为ASCII*/
    private static char hexToAscii(int h) {
        if((h >= 10) && (h <= 15)) {
            return (char) ('A' + (h - 10));
        }
        if((h >= 0) && (h <= 9)) {
            return (char) ('0' + h);
        }
        throw new RuntimeException("hex转换为ASCII失败！！！");
    }

    /**解密*/
    public static String getDecryptStr(String key, String str) {
        byte[] es = decryptStr(key.getBytes(), fromString(str));
        return es == null ? null : substr(StringEscapeUtils.unescapeJavaScript(new String(es)));
    }

    private static String substr(String oldStr) {
        if(oldStr.startsWith("\"")) {
            return oldStr.substring(1, oldStr.length() - 1);
        }
        return oldStr;
    }
    private static byte[] decryptStr(byte[] keybyte, byte[] src) {
        try {
            /**生成密钥*/
            SecretKeySpec deskey = new SecretKeySpec(keybyte, ALGORITHM);
            /**解密*/
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
            logger.info("文件解密初始化失败！");
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e2) {
            logger.info("文件解密失败！");
        }
        return null;
    }

    /**将16进制转换为2进制*/
    public static byte[] fromString(String inHex) {
        int len = inHex.length();
        int pos = 0;
        byte buffer[] = new byte[((len + 1) / 2)];

        try {
            if((len % 2) == 1) {
                buffer[0] = (byte) asciiToHex(inHex.charAt(0));
                pos = 1;
                len --;
            }

            for(int ptr = pos; len > 0; len -= 2) {
                buffer[pos++] = (byte) ((asciiToHex(inHex.charAt(ptr++))) << 4 | (asciiToHex(inHex.charAt(ptr++))));
            }
        } catch (Exception e) {
            logger.info("后端解密异常，请检查数据格式！", e);
            throw new RuntimeException("后端解密异常，请检查数据格式！");
        }
        return buffer;
    }

    /**将ASCII转换为hex*/
    private static int asciiToHex(char c) {
        if((c >= 'a') && (c <= 'f')) {
            return (c - 'a' + 10);
        }
        if((c >= 'A') && (c <= 'F')) {
            return (c - 'A' + 10);
        }
        if((c >= '0') && (c <= '9')) {
            return (c - '0');
        }
        throw new RuntimeException("将ASCII转换为hex失败！");
    }

public static void main(String[] args) {
    String encrypeStr = getEncrypeStr("abcdabcdabcdabcdabcdabcd", "123456");
    System.out.println(encrypeStr);
    String decryptStr = getDecryptStr("abcdabcdabcdabcdabcdabcd", "8EC66968E9884D4B");
    System.out.println(decryptStr);
}
}
