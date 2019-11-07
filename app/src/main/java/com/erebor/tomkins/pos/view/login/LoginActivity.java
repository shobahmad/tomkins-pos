package com.erebor.tomkins.pos.view.login;

import android.content.Intent;
import android.os.Bundle;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityLoginBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.buttonLogin.setOnClickListener(v -> {
            binding.setLoading(true);
            binding.buttonLogin.postDelayed(() -> {
                binding.setLoading(false);
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            }, 2000);
        });
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }
}
