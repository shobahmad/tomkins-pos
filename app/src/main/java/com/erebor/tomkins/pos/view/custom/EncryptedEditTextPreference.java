package com.erebor.tomkins.pos.view.custom;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;

import com.erebor.tomkins.pos.tools.SecurityEncryption;

public class EncryptedEditTextPreference extends EditTextPreference {

    private SecurityEncryption securityEncryption;

    public EncryptedEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(String text) {
        if (securityEncryption == null) {
            super.setText(text);
            return;
        }
        try {
            text = securityEncryption.aesEncrypt(text);
        } catch (Exception e) {
        }
        super.setText(text);
    }

    public void setEncryptedSummaryProvider(SecurityEncryption securityEncryption, String defaultValue) {
        if (securityEncryption == null) {
            return;
        }

        this.securityEncryption = securityEncryption;
        setOnBindEditTextListener(editText -> {
            String decrypted = "";
            try {
                decrypted = securityEncryption.aesDecrypt(editText.getText().toString());
            } catch (Exception e) {
            }
            editText.setText(decrypted);
            editText.setSelection(decrypted.length());
        });

        setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) preference -> {
            String text = preference.getText();
            if (TextUtils.isEmpty(text)){
                return defaultValue;
            }
            String decrypted = text;
            try {
                decrypted = securityEncryption.aesDecrypt(text);
            } catch (Exception e) {
            }
            return decrypted;
        });
    }
}
