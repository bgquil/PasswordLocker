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
 * Created by Other on 6/30/2017.
 */

public class Crypto {
    // TODO: 7/4/2017 ECB insecure
    private static final String CIPHERSPEC = "AES/ECB/PKCS5Padding";


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



    private static SecretKeySpec generateKeySpec(String inKey){
        try {
            byte[] key = inKey.getBytes("UTF-8");
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
