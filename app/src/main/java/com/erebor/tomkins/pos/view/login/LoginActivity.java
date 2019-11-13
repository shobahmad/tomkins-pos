package com.erebor.tomkins.pos.view.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityLoginBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewModel;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewState;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        observerChange();

        binding.buttonLogin.setOnClickListener(v -> {
            viewModel.getLogin(binding.editName.getText().toString(), binding.editPassword.getText().toString());
        });
    }

    private void observerChange() {
        viewModel.getViewState().observe(this, loginViewState -> {
            if (loginViewState.getCurrentState() == LoginViewState.LOADING_STATE.getCurrentState()) {
                binding.setLoading(true);
                binding.setError(null);
                return;
            }

            if (loginViewState.getCurrentState() == LoginViewState.ERROR_STATE.getCurrentState()) {
                binding.setLoading(false);
                binding.setError(loginViewState.getError().getMessage());
                return;
            }

            if (loginViewState.getCurrentState() == LoginViewState.LOGIN_VALID_STATE.getCurrentState()) {
                binding.setLoading(false);
                binding.setError(null);
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            }


        });
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }
}
