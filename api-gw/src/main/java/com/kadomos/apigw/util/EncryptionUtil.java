package com.kadomos.apigw.util;

import com.kadomos.apigw.exception.KadomosException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * Utility class for use in File Encryption.
 *
 */
public class EncryptionUtil {

    private static final String SECURITY_KEY_CODE = "PBKDF2WithHmacSHA512";
    private static final String CIPHER_KEY_CODE = "AES/CBC/PKCS5Padding";
    private static final int ITERATION_COUNT = 99;
    private static final int KEY_LENGTH = 128;

    /**
     * Encrypt the given input using the security key
     *
     * @param input the input to be encrypted
     * @param securityKey the security key provided to encrypt
     * @return the encrypted input
     */
    public static String encrypt(String input, String securityKey) throws KadomosException {
        try {
            SecretKeySpec key = createSecretKey(securityKey);
            Cipher pbeCipher = Cipher.getInstance(CIPHER_KEY_CODE);
            pbeCipher.init(Cipher.ENCRYPT_MODE, key);
            AlgorithmParameters parameters = pbeCipher.getParameters();
            IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
            byte[] crypto = pbeCipher.doFinal(input.getBytes("UTF-8"));
            byte[] iv = ivParameterSpec.getIV();
            return base64Encode(iv) + ":" + base64Encode(crypto);
        } catch(Exception ex) {
            throw new KadomosException(ex.getMessage());
        }
    }

    /**
     * Decrypt the given input using the security key
     *
     * @param input the input to be decrypted
     * @param securityKey the security key provided to decrypt
     * @return the decrypted input
     */
    public static String decrypt(String input, String securityKey) throws KadomosException {
        String result = input;
        try {
            String[] inputs = input.split(":");
            if (inputs.length == 2) {
                String iv = inputs[0];
                String crypto = inputs[1];
                SecretKeySpec key = createSecretKey(securityKey);
                Cipher pbeCipher = Cipher.getInstance(CIPHER_KEY_CODE);
                pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
                result = new String(pbeCipher.doFinal(base64Decode(crypto)), "UTF-8");
            } else {
                throw new KadomosException("Not supported input format");
            }
        } catch(Exception ex) {
            throw new KadomosException(ex.getMessage());
        }
        return result;
    }

    private static SecretKeySpec createSecretKey(String securityKey) throws KadomosException {
        //read this configuration from external
        byte[] salt = new StringBuffer(securityKey).reverse().toString().getBytes();
        SecretKeySpec secretKeySpec = null;
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECURITY_KEY_CODE);
            PBEKeySpec keySpec = new PBEKeySpec(securityKey.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKey keyTmp = keyFactory.generateSecret(keySpec);
            secretKeySpec = new SecretKeySpec(keyTmp.getEncoded(), "AES");
        } catch(Exception ex) {
            throw new KadomosException(ex.getMessage());
        }
        return secretKeySpec;
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }

    /**
     * Generate salt and use create secure hash of the provided key.
     * @param key the key to securely hash
     * @return the secure hash and salt
     * @throws KadomosException if the MessageDigest cannot be loaded.
     */
    public static SecureHashedKeyAndSalt generateHashedKeyAndSalt(final String key) throws KadomosException {
        return SecureHashedKeyAndSalt.builder()
                .withKey(key)
                .build();
    }

    /**
     * Container for the secure hashed key and salt.
     */
    public static class SecureHashedKeyAndSalt {
        private final String key;
        private final String encodedKey;
        private final String salt;

        private SecureHashedKeyAndSalt(String key, String encodedKey, String salt) {
            this.key = key;
            this.encodedKey = encodedKey;
            this.salt = salt;
        }

        public String getKey() {
            return key;
        }

        public String getEncodedKey() {
            return encodedKey;
        }

        public String getSalt() {
            return salt;
        }

        public static Builder builder() {
            return new Builder();
        }

        static class Builder {
            private String key;

            Builder withKey(String key) {
                this.key = key;
                return this;
            }

            SecureHashedKeyAndSalt build() throws KadomosException {

                MessageDigest digest;
                try {
                    digest = MessageDigest.getInstance("SHA-256");

                    final Random r = new SecureRandom();
                    byte[] salt = new byte[32];
                    r.nextBytes(salt);

                    digest.reset();
                    digest.update(salt);
                    byte[] btPass = digest.digest(key.getBytes(StandardCharsets.UTF_8));
                    digest.reset();
                    btPass = digest.digest(btPass);

                    String encodedKey = org.apache.commons.codec.binary.Base64.encodeBase64String(btPass);
                    String encodedSalt = org.apache.commons.codec.binary.Base64.encodeBase64String(salt);
                    return new SecureHashedKeyAndSalt(key, encodedKey, encodedSalt);
                } catch (NoSuchAlgorithmException e) {
                    throw new KadomosException(e.getMessage());
                }
            }
        }
    }
}