package com.erebor.tomkins.pos.view.login;

import android.os.Bundle;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityMainBinding;
import com.erebor.tomkins.pos.di.AppComponent;

public class LoginActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.text.setText("Login");
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}
