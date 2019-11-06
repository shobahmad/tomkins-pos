package com.erebor.tomkins.pos.factory;

import android.util.Base64;

import com.erebor.tomkins.pos.BuildConfig;
import com.erebor.tomkins.pos.tools.SecurityEncryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;

@Singleton
public class SecurityEncryptionFactory {

    private static SecurityEncryptionFactory encryptionFactory;
    private SecurityEncryption encryption;

    private SecurityEncryptionFactory() {
        encryption = new SecurityEncryption() {

            final int pswdIterations = 10;
            final int keySize = 128;
            final String cypherInstance = "AES/CBC/PKCS5Padding";
            final String secretKeyInstance = "PBKDF2WithHmacSHA1";
            final String plainText = BuildConfig.SALTKEY;
            final String AESSalt = BuildConfig.SALTKEY;
            final String initializationVector = "8119745113154120";

            @Override
            public String md5(String string) {
                byte[] hash;

                try {
                    hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException("Huh, MD5 should be supported?", e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Huh, UTF-8 should be supported?", e);
                }

                StringBuilder hex = new StringBuilder(hash.length * 2);

                for (byte b : hash) {
                    int i = (b & 0xFF);
                    if (i < 0x10) hex.append('0');
                    hex.append(Integer.toHexString(i));
                }

                return hex.toString();
            }

            @Override
            public String aesEncrypt(String textToEncrypt) throws Exception {
                SecretKeySpec skeySpec = new SecretKeySpec(getRaw(plainText, AESSalt), "AES");
                Cipher cipher = Cipher.getInstance(cypherInstance);
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(initializationVector.getBytes()));
                byte[] encrypted = cipher.doFinal(textToEncrypt.getBytes());
                return Base64.encodeToString(encrypted, Base64.DEFAULT);
            }

            @Override
            public String aesDecrypt(String textToDecrypt) throws Exception {
                byte[] encryted_bytes = Base64.decode(textToDecrypt, Base64.DEFAULT);
                SecretKeySpec skeySpec = new SecretKeySpec(getRaw(plainText, AESSalt), "AES");
                Cipher cipher = Cipher.getInstance(cypherInstance);
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(initializationVector.getBytes()));
                byte[] decrypted = cipher.doFinal(encryted_bytes);
                return new String(decrypted, "UTF-8");
            }

            @Override
            public String sh1(String text) throws Exception {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(text.getBytes("iso-8859-1"), 0, text.length());
                byte[] sha1hash = md.digest();
                return convertToHex(sha1hash);

            }

            private String convertToHex(byte[] data) {
                StringBuilder buf = new StringBuilder();
                for (byte b : data) {
                    int halfbyte = (b >>> 4) & 0x0F;
                    int two_halfs = 0;
                    do {
                        buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte)
                                : (char) ('a' + (halfbyte - 10)));
                        halfbyte = b & 0x0F;
                    } while (two_halfs++ < 1);
                }
                return buf.toString();
            }


            private byte[] getRaw(String plainText, String salt) {
                try {
                    SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyInstance);
                    KeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt.getBytes(), pswdIterations, keySize);
                    return factory.generateSecret(spec).getEncoded();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        };
    }

    public static SecurityEncryptionFactory getInstance() {
        if (encryptionFactory != null) {
            return encryptionFactory;
        }

        return new SecurityEncryptionFactory();
    }

    public SecurityEncryption getEncryption() {
        return encryption;
    }
}
