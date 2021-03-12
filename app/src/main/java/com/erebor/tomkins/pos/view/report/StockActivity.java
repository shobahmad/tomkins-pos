package com.erebor.tomkins.pos.view.report;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.ReportSummaryUiModel;
import com.erebor.tomkins.pos.data.ui.StockUiModel;
import com.erebor.tomkins.pos.databinding.ActivityStockBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.viewmodel.report.StockReportViewModel;
import com.erebor.tomkins.pos.viewmodel.report.StockReportViewState;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StockActivity extends BaseActivity<ActivityStockBinding> {

    private StockReportAdapter stockReportAdapter;

    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    ViewModelProvider.Factory factory;
    private StockReportViewModel stockReportViewModel;

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_stock;
    }

    @Override
    protected boolean isMenuSearchEnabled() {
        return true;
    }

    @Override
    protected boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        return super.onQueryTextSubmit(query);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(binding.toolbar.toolbar);

        stockReportViewModel = ViewModelProviders.of(this, factory).get(StockReportViewModel.class);

        stockReportAdapter = new StockReportAdapter(StockActivity.this, dateConverterHelper);
        binding.recyclerStock.setLayoutManager(new LinearLayoutManager(StockActivity.this));
        binding.recyclerStock.setAdapter(stockReportAdapter);

        startObserver();
        stockReportViewModel.getStockLatest();
    }

    private void startObserver() {
        stockReportViewModel.getViewState().observe(this, state -> {
            if (state.getCurrentState() == StockReportViewState.FOUND_STATE.getCurrentState()) {
                stockReportAdapter.addList(state.getData());
            }
        });
    }


}
