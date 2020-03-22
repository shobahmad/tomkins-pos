package com.erebor.tomkins.pos.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.TomkinApps;
import com.erebor.tomkins.pos.tools.SecurityEncryption;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.custom.EncryptedEditTextPreference;
import com.erebor.tomkins.pos.view.login.LoginActivity;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewModel;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewState;

import javax.inject.Inject;

public class SettingPrefsFragment extends PreferenceFragmentCompat {

    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    SecurityEncryption securityEncryption;
    @Inject
    ViewModelProvider.Factory factory;

    LoginViewModel viewModel;

    public SettingPrefsFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startObserver();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        diInjection();
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);

        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        EditTextPreference editHostPrefs = findPreference(getResources().getString(R.string.key_setting_host));
        editHostPrefs.setOnBindEditTextListener(editText -> editText.setText(sharedPrefs.getHostname()));
        editHostPrefs.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) preference -> {
            String text = preference.getText();
            if (TextUtils.isEmpty(text)){
                return sharedPrefs.getHostname();
            }
            return text;
        });

        ListPreference listScanner = findPreference(getResources().getString(R.string.setting_key_camera));
        listScanner.setSummaryProvider((Preference.SummaryProvider<ListPreference>) preference -> {
            if (preference.getEntry() == null) {
                return getResources().getString(R.string.scanner_camera);
            }
            return preference.getEntry();
        });


        EditTextPreference kodeSPG = findPreference(getResources().getString(R.string.key_kode_spg));
        kodeSPG.setSummary(sharedPrefs.getKodeSPG());
        EditTextPreference namaSPG = findPreference(getResources().getString(R.string.key_nama_spg));
        namaSPG.setSummary(sharedPrefs.getNamaSPG());
        EditTextPreference kodeKonter = findPreference(getResources().getString(R.string.key_kode_konter));
        kodeKonter.setSummary(sharedPrefs.getKodeKonter());
        EditTextPreference namaKonter = findPreference(getResources().getString(R.string.key_nama_konter));
        namaKonter.setSummary(sharedPrefs.getNamaKonter());
        Preference userName = findPreference(getResources().getString(R.string.session_key_username));
        userName.setSummary(sharedPrefs.getUsername());
        userName.setOnPreferenceClickListener(preference -> {
            viewModel.postLogout();
            return false;
        });

    }

    void startObserver() {
        viewModel.getViewState().observe(getViewLifecycleOwner(), loginViewState -> {
            if (loginViewState.getCurrentState() == LoginViewState.LOGOUT_VALID_STATE.getCurrentState()) {
                getActivity().finish();
                return;
            }
            if (loginViewState.getCurrentState() == LoginViewState.ERROR_STATE.getCurrentState()) {
                getActivity().finish();
            }
        });
    }

    void diInjection() {
        TomkinApps mainApplication = (TomkinApps) getActivity().getApplication();
        mainApplication.getAppComponent().doInjection(this);
    }
}
