package com.erebor.tomkins.pos.view.setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivitySettingBinding;
import com.erebor.tomkins.pos.di.AppComponent;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    @Override
    public void inject(AppComponent appComponent) {
    }

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new SettingPrefsFragment(), "SettingPrefsFragment")
                .commit();
    }
}
