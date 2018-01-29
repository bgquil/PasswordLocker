package core;

import com.sun.xml.internal.bind.v2.TODO;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Benjamin Quilliams on 6/30/2017.
 */

public class Crypto {
    private static final String CIPHERSPEC = "AES/ECB/PKCS5Padding";


    /**
     * Returns a Cipher object in decrypt mode with the provided password.
     * @param pw The plaintext password used for encrypting/decrypting a Locker object.
     * @return Cipher used for decryption
     */
    public static Cipher getCipherDecrypt(String pw){
        try {
            final Cipher usedCipher = Cipher.getInstance(CIPHERSPEC);
            usedCipher.init(Cipher.DECRYPT_MODE, Crypto.generateKeySpec(pw));
            return usedCipher;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a Cipher object in encrypt mode with the provided password.
     * @param pw The plaintext password used for encrypting/decrypting a Locker object.
     * @return Cipher used for encryption
     */
    public static Cipher getCipherEncrypt(String pw){
        try {
            final Cipher usedCipher = Cipher.getInstance(CIPHERSPEC);
            usedCipher.init(Cipher.ENCRYPT_MODE, Crypto.generateKeySpec(pw));
            return usedCipher;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Used to generate a SecretKeySpec needed to initialize a Cipher.
     * @param pw user supplied password
     * @return
     */
    private static SecretKeySpec generateKeySpec(String pw){
        try {
            byte[] key = pw.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use first 128 bits
            SecretKeySpec sks = new SecretKeySpec(key,"AES");
            return sks;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }








}
