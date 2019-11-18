package com.erebor.tomkins.pos.view.report;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.ReportSummaryUiModel;
import com.erebor.tomkins.pos.data.ui.StockUiModel;
import com.erebor.tomkins.pos.databinding.ActivityStockBinding;
import com.erebor.tomkins.pos.di.AppComponent;

import java.util.ArrayList;
import java.util.List;

public class StockActivity extends BaseActivity<ActivityStockBinding> {

    private StockReportAdapter stockReportAdapter;

    @Override
    public void inject(AppComponent appComponent) {
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

        stockReportAdapter = new StockReportAdapter(StockActivity.this);
        binding.recyclerStock.setLayoutManager(new LinearLayoutManager(StockActivity.this));
        binding.recyclerStock.setAdapter(stockReportAdapter);

        fetchStock();
    }


    private void fetchStock() {
        ReportSummaryUiModel reportSummaryUiModel = new ReportSummaryUiModel();
        reportSummaryUiModel.setStockTotal(120);
        reportSummaryUiModel.setStockIncoming(100);
        reportSummaryUiModel.setStockOutgoing(82);
        binding.setSummary(reportSummaryUiModel);

        List<StockUiModel> stockUiModelList = new ArrayList<StockUiModel>();
        for (int i = 1; i < 21; i++) {
            StockUiModel stockUiModel = new StockUiModel();
            stockUiModel.setProductId("P1JE"+i);
            stockUiModel.setDoId("QZSD12312F");
            stockUiModel.setName("Falcon All Black " + i);
            stockUiModel.setQty(i * 4);
            stockUiModel.setPrice(i % 3 == 0 ? 319000d : 402000d);
            stockUiModel.setSize("42");
            stockUiModel.setDescription("...");
            stockUiModelList.add(stockUiModel);
        }
        stockReportAdapter.addList(stockUiModelList);
    }
}
