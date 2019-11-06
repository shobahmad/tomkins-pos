package com.erebor.tomkins.pos.base;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.erebor.tomkins.pos.TomkinApps;
import com.erebor.tomkins.pos.di.AppComponent;


public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TomkinApps mainApplication = (TomkinApps) getApplication();
        inject(mainApplication.getAppComponent());
        binding = DataBindingUtil.setContentView(this, getLayout());
    }

    public abstract void inject(AppComponent appComponent);
    public abstract int getLayout();


    protected void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
