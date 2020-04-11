package com.erebor.tomkins.pos.view.setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.TomkinApps;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.SecurityEncryption;
import com.erebor.tomkins.pos.tools.SharedPrefs;
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
    @Inject
    ResourceHelper resourceHelper;

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

        ListPreference listScanner = findPreference(getResources().getString(R.string.setting_key_camera));
        listScanner.setSummaryProvider((Preference.SummaryProvider<ListPreference>) preference -> {
            if (preference.getEntry() == null) {
                return getResources().getString(R.string.scanner_camera);
            }
            return preference.getEntry();
        });

        ListPreference listInterval = findPreference(getResources().getString(R.string.setting_key_auto_download_interval));
        listInterval.setSummaryProvider((Preference.SummaryProvider<ListPreference>) preference -> {
            if (preference.getEntry() == null) {
                return getResources().getString(R.string.minutes_5);
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
            confirmationDialog();
            return false;
        });

    }

    private void confirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Dialog);
        builder.setTitle(resourceHelper.getResourceString(R.string.logout));
        builder.setMessage(resourceHelper.getResourceString(R.string.logout_confirm));

        builder.setPositiveButton(R.string.yes, (dialog, which) -> viewModel.logout());
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void startObserver() {
        viewModel.getViewState().observe(getViewLifecycleOwner(), loginViewState -> {
            if (loginViewState.getCurrentState().equals(LoginViewState.LOGOUT_VALID_STATE.getCurrentState())) {
                getActivity().finish();
                return;
            }
            if (loginViewState.getCurrentState().equals(LoginViewState.ERROR_STATE.getCurrentState())) {
                getActivity().finish();
            }
        });
    }

    void diInjection() {
        TomkinApps mainApplication = (TomkinApps) getActivity().getApplication();
        mainApplication.getAppComponent().doInjection(this);
    }
}
