package com.erebor.tomkins.pos.data.model;

import androidx.annotation.NonNull;

import com.erebor.tomkins.pos.factory.SecurityEncryptionFactory;
import com.erebor.tomkins.pos.tools.SecurityEncryption;

public class EncryptedAesField {

    private static final String TAG = "EncryptedAesField";
    private SecurityEncryption encryption;
    private final String encrypted;
    private String decrypted;

    public EncryptedAesField(@NonNull String value) {
        this.encryption = SecurityEncryptionFactory.getInstance().getEncryption();
        this.encrypted = encrypt(value);
    }
    public String getDecryptedValue() {
        try {
            if (decrypted != null) {
                return decrypted;
            }
            return decrypted = encryption.aesDecrypt(encrypted);
        } catch (Exception e) {
            return null;
        }
    }
    public String getEncryptedValue() {
        try {
            return encrypted;
        } catch (Exception e) {
            return null;
        }
    }

    private String encrypt(@NonNull String value) {
        try {
            return encryption.aesEncrypt(value);
        } catch (Exception e) {
            return null;
        }
    }

}
