package com.erebor.tomkins.pos.view.setting

import android.os.Bundle

import androidx.preference.PreferenceFragmentCompat

import com.erebor.tomkins.pos.R

class SettingPrefsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey)
    }
}
