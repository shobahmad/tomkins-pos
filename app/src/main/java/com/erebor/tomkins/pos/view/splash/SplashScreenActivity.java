package com.erebor.tomkins.pos.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivitySplashBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity;
import com.erebor.tomkins.pos.view.login.LoginActivity;
import com.erebor.tomkins.pos.viewmodel.splash.SplashViewModel;
import com.erebor.tomkins.pos.viewmodel.splash.SplashViewState;

import javax.inject.Inject;

public class SplashScreenActivity extends BaseActivity<ActivitySplashBinding> {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    SplashViewModel splashViewModel;

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        observeDataChange();
        splashViewModel.sessionCheck();
    }

    private void observeDataChange() {
        splashViewModel.getViewState().observe(this, splashViewState -> {
            if (splashViewState.getCurrentState() == SplashViewState.LOADING_STATE.getCurrentState()) {
                binding.setProgress(splashViewState.getProgress());
                return;
            }

            if (splashViewState.getCurrentState() == SplashViewState.SESSION_EMPTY_STATE.getCurrentState()) {
                binding.setProgress(splashViewState.getProgress());
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            }

            if (splashViewState.getCurrentState() == SplashViewState.SESSION_VALID_STATE.getCurrentState()) {
                binding.setProgress(splashViewState.getProgress());
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
                return;
            }
            if (splashViewState.getCurrentState() == SplashViewState.ERROR_STATE.getCurrentState()) {
                binding.setProgress(splashViewState.getProgress());
                Toast.makeText(this, splashViewState.getError().getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}
