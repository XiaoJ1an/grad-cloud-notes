package com.university.gradcloudnotes.utils;

import com.university.gradcloudnotes.rest.NoteController;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
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
}
