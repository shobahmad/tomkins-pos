package com.erebor.tomkins.pos.view.receive;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;
import com.erebor.tomkins.pos.databinding.ActivityReceiveStockBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewModel;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewState;

import javax.inject.Inject;

public class ProductReceiveStockActivity extends BaseActivity<ActivityReceiveStockBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    ProductReceiveStockViewModel productReceiveStockViewModel;
    private ReceiveStockAdapter receiveStockAdapter;


    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_receive_stock;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(binding.toolbar.toolbar);

        productReceiveStockViewModel = ViewModelProviders.of(this, factory).get(ProductReceiveStockViewModel.class);
        startObserver();
        setupAdapter();
        String noDo = getIntent().getStringExtra("NO_DO");
        productReceiveStockViewModel.loadData(noDo);
        binding.setSubtitle(noDo);
    }

    private void setupAdapter() {
        receiveStockAdapter = new ReceiveStockAdapter(this, (trxTerimaStockModel, qty) -> {
            Log.d(getClass().getSimpleName(), trxTerimaStockModel +" -> " + qty);
        });
        binding.recyclerTrxTerima.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerTrxTerima.setAdapter(receiveStockAdapter);

    }

    private void startObserver() {
        productReceiveStockViewModel.getViewState().observe(this, state -> {
            if (state.getCurrentState().equals(ProductReceiveStockViewState.FOUND_STATE.getCurrentState())) {
                receiveStockAdapter.addList(state.getData());
            }
        });
    }
}
