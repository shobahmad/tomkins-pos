package com.erebor.tomkins.pos.view.receive;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.ProductReceiveUiModel;
import com.erebor.tomkins.pos.databinding.ActivityProductReceiveBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewModel;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveViewModel;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveViewState;

import javax.inject.Inject;

public class ProductReceiveActivity extends BaseActivity<ActivityProductReceiveBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    ProductReceiveViewModel productReceiveViewModel;

    private ProductReceiveAdapter productReceiveAdapter;

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

        setToolbar(binding.toolbar.toolbar);
        productReceiveViewModel = ViewModelProviders.of(this, factory).get(ProductReceiveViewModel.class);
        startObserver();
        setupAdapter();
        productReceiveViewModel.loadDeliveryOrder();
    }

    private void setupAdapter() {
        productReceiveAdapter = new ProductReceiveAdapter(this);
        productReceiveAdapter.addListener(item -> {
            ProductReceiveUiModel trxTerima = (ProductReceiveUiModel) item;
            Intent intent = new Intent(ProductReceiveActivity.this, ProductReceiveStockActivity.class);
            intent.putExtra("NO_DO", trxTerima.getNoDo());
            startActivity(intent);
        });

        binding.recyclerTrxTerima.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerTrxTerima.setAdapter(productReceiveAdapter);
    }

    private void startObserver() {
        productReceiveViewModel.getViewState().observe(this, state -> {
            if (state.getCurrentState().equals(ProductReceiveViewState.FOUND_STATE.getCurrentState())) {
                productReceiveAdapter.setList(state.getData());
            }
        });
    }
}
