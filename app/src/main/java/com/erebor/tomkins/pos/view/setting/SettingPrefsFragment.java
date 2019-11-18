package com.erebor.tomkins.pos.view.setting;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.TomkinApps;
import com.erebor.tomkins.pos.tools.SecurityEncryption;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.custom.EncryptedEditTextPreference;

import javax.inject.Inject;

public class SettingPrefsFragment extends PreferenceFragmentCompat {

    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    SecurityEncryption securityEncryption;

    public SettingPrefsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        diInjection();
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);

        EditTextPreference editHostPrefs = findPreference(getResources().getString(R.string.key_setting_host));
        editHostPrefs.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) preference -> {
            String text = preference.getText();
            if (TextUtils.isEmpty(text)){
                return sharedPrefs.getHostname();
            }
            return text;
        });

        EncryptedEditTextPreference editUsername = findPreference(getResources().getString(R.string.key_setting_email));
        editUsername.setEncryptedSummaryProvider(securityEncryption, getResources().getString(R.string.setting_email));

        EncryptedEditTextPreference editPassword = findPreference(getResources().getString(R.string.key_setting_password));
        editPassword.setEncryptedSummaryProvider(securityEncryption, "******");
        editPassword.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) preference -> "********");



    }

    void diInjection() {
        TomkinApps mainApplication = (TomkinApps) getActivity().getApplication();
        mainApplication.getAppComponent().doInjection(this);
    }
}
