package com.erebor.tomkins.pos.view.report;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.local.model.MsGenderDBModel;
import com.erebor.tomkins.pos.data.ui.ReportSummaryUiModel;
import com.erebor.tomkins.pos.data.ui.StockUiModel;
import com.erebor.tomkins.pos.databinding.ActivityStockBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.viewmodel.report.GenderViewModel;
import com.erebor.tomkins.pos.viewmodel.report.GenderViewState;
import com.erebor.tomkins.pos.viewmodel.report.StockReportViewModel;
import com.erebor.tomkins.pos.viewmodel.report.StockReportViewState;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StockActivity extends BaseActivity<ActivityStockBinding> {

    private StockReportAdapter stockReportAdapter;

    @Inject
    ViewModelProvider.Factory factory;
    private StockReportViewModel stockReportViewModel;
    private GenderViewModel genderViewModel;

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
    protected int getSearchableMenu() {
        return R.menu.report_stock;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            binding.setFilterEnable(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getActionSearchMenuItem() {
        return R.id.action_search_art;
    }

    @Override
    protected boolean onQueryTextSubmit(String query) {
        applyFilter();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(binding.toolbar.toolbar);

        stockReportViewModel = ViewModelProviders.of(this, factory).get(StockReportViewModel.class);
        genderViewModel = ViewModelProviders.of(this, factory).get(GenderViewModel.class);

        stockReportAdapter = new StockReportAdapter(StockActivity.this);
        binding.recyclerStock.setLayoutManager(new LinearLayoutManager(StockActivity.this));
        binding.recyclerStock.setAdapter(stockReportAdapter);

        setupFilterSizeAutoComplete();
        startObserver();
        genderViewModel.loadAllGender();
        stockReportViewModel.getStockLatest();
    }

    private void startObserver() {
        stockReportViewModel.getViewState().observe(this, state -> {
            if (state.getCurrentState().equals(StockReportViewState.FOUND_STATE.getCurrentState())) {
                stockReportAdapter.addList(state.getData());
                return;
            }
            if (state.getCurrentState().equals(StockReportViewState.NOT_FOUND_STATE.getCurrentState())) {
                stockReportAdapter.clearList();
                Toast.makeText(StockActivity.this, "Not found!", Toast.LENGTH_LONG).show();
                return;
            }
            if (state.getCurrentState().equals(StockReportViewState.ERROR_STATE.getCurrentState())) {
                stockReportAdapter.clearList();
                Toast.makeText(StockActivity.this, state.getError().getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

        genderViewModel.getViewState().observe(this, state -> {
            if (state.getCurrentState().equals(GenderViewState.FOUND_STATE.getCurrentState())) {
                String[] genders = new String[state.getData().size()];
                for (int i = 0; i < state.getData().size(); i++) {
                    genders[i] = state.getData().get(i).getGender();
                }
                setupFilterGenderAutoComplete(genders);
            }
        });
    }

    private void setupFilterSizeAutoComplete() {
        String[] SIZES = new String[18];
        for (int i = 30; i <= 46; i++) {
            SIZES[i - 30] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        SIZES);

        binding.editFilterSize.setAdapter(adapter);
        binding.editFilterSize.setOnItemClickListener((parent, view, position, id) -> {
            binding.setSize(((TextView) view).getText().toString());
            applyFilter();
        });
        binding.buttonClearFilterSize.setOnClickListener(v -> {
            binding.setSize(null);
            binding.textFilterSize.clearFocus();
            applyFilter();
        });

    }
    private void setupFilterGenderAutoComplete(String[] genders) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        genders);
        binding.editFilterGender.setAdapter(adapter);
        binding.editFilterGender.setOnItemClickListener((parent, view, position, id) -> {
            binding.setGender(((TextView) view).getText().toString());
            applyFilter();
        });
        binding.buttonClearFilterGender.setOnClickListener(v -> {
            binding.setGender(null);
            binding.textFilterGender.clearFocus();
            applyFilter();
        });
    }

    private void applyFilter() {
        String searchQuery = getSearhQueryText();
        stockReportViewModel.getStock(searchQuery.trim().isEmpty() ? null : searchQuery.trim() , binding.getSize(), binding.getGender());
    }
}
