package com.erebor.tomkins.pos.view.dashboard;

import android.os.Bundle;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityMainBinding;
import com.erebor.tomkins.pos.di.AppComponent;

public class DashboardActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.text.setText("Dashboard");
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}
