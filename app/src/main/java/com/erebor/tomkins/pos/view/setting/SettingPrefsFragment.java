package com.erebor.tomkins.pos.view.setting;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.erebor.tomkins.pos.R;

public class SettingPrefsFragment extends PreferenceFragmentCompat {

    public SettingPrefsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);
    }
}
