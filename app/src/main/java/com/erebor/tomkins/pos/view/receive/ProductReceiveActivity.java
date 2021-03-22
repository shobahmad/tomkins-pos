package com.erebor.tomkins.pos.view.receive;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityProductReceiveBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveViewModel;

import javax.inject.Inject;

public class ProductReceiveActivity extends BaseActivity<ActivityProductReceiveBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    ProductReceiveViewModel productReceiveViewModel;

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_product_receive;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productReceiveViewModel = ViewModelProviders.of(this, factory).get(ProductReceiveViewModel.class);
        startObserver();
        setupAdapter();
        productReceiveViewModel.loadDeliveryOrder();
    }

    private void setupAdapter() {

    }

    private void startObserver() {
        productReceiveViewModel.getViewState().observe(this, state -> {

        });
    }
}