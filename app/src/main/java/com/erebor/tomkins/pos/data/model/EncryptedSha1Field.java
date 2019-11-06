package com.erebor.tomkins.pos.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.erebor.tomkins.pos.factory.SecurityEncryptionFactory;
import com.erebor.tomkins.pos.tools.SecurityEncryption;

public class EncryptedSha1Field {

    private SecurityEncryption encryption;
    private String encrypted;

    public EncryptedSha1Field() {
        this.encryption = SecurityEncryptionFactory.getInstance().getEncryption();
    }

    public EncryptedSha1Field(@NonNull String value) {
        this.encryption = SecurityEncryptionFactory.getInstance().getEncryption();
        this.encrypted = encrypt(value);
    }
    public String getValue() {
        try {
            return encrypted;
        } catch (Exception e) {
            return null;
        }
    }

    public void setValue(String value) {
        this.encrypted = value;
    }

    private String encrypt(@NonNull String value) {
        try {
            String encrypted = encryption.sh1(value);
            Log.d("EncryptedSha1Field", "Encrypting '" + value + "' --> '" + encrypted+"'");
            return encrypted;
        } catch (Exception e) {
            return null;
        }
    }

}
