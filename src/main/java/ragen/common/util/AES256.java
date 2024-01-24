package ragen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class AES256 {
    private static String alg;
    private static String key;
    private static String algorithm;
    private static String charset;
    private static String iv;

    @Autowired
    public AES256(@Value("${aes256.cipher}") String alg,
                  @Value("${aes256.key}") String key,
                  @Value("${aes256.algorithm}") String algorithm,
                  @Value("${aes256.encoding.charset}") String charset)
    {
        this.alg = alg;
        this.key = key;
        this.algorithm = algorithm;
        this.charset = charset;
        this.iv = key.substring(0, 16);
    }

    public String getAlg(){
        return  alg;
    }
    public static String encrypt(String text) throws Exception
    {
        if(!isEncrypted(text))
        {
            if (StringUtils.isNotEmpty(text))
            {
                Cipher cipher = Cipher.getInstance(alg);
                SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);

                byte[] encrypted = cipher.doFinal(text.getBytes(charset));
                return Base64.getEncoder().encodeToString(encrypted);
            }
        }

        return text;
    }

    public static String decrypt(String cipherText) throws Exception
    {
        if(StringUtils.isNotEmpty(cipherText))
        {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), algorithm);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, charset);
        }

        return cipherText;
    }

    public static boolean isEncrypted(String text)
    {
        try {
            String decrypted = decrypt(text);
            return !decrypted.equals(text);  // 복호화한 결과가 원본과 같다면 암호화되지 않은 데이터로 판단
        } catch (Exception e) {
            return false; // 복호화에 실패하면 암호화되지 않은 데이터로 판단
        }
    }
}

