package com.erebor.tomkins.pos.view.receive;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityReceiveStockBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.scan.VisionScannerActivity;
import com.erebor.tomkins.pos.view.scan.ZynxScannerActivity;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewModel;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewState;

import javax.inject.Inject;

public class ProductReceiveStockActivity extends BaseActivity<ActivityReceiveStockBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    ResourceHelper resourceHelper;
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
        setupSearchView();
        setupScanButton();
        String noDo = getIntent().getStringExtra("NO_DO");
        productReceiveStockViewModel.loadData(noDo);
        binding.setSubtitle(noDo);
    }

    private void setupScanButton() {
        binding.buttonScan.setOnClickListener(v -> startScannerActivity());
    }


    private void startScannerActivity() {
        String scanner = sharedPrefs.getString(getResources().getString(R.string.setting_key_camera), "");
        if (scanner.equals("")) {
            startActivityForResult(new Intent(ProductReceiveStockActivity.this, ZynxScannerActivity.class), 1);
            return;
        }

        if (scanner.equals("zxing")) {
            startActivityForResult(new Intent(ProductReceiveStockActivity.this, ZynxScannerActivity.class), 1);
            return;
        }

        startActivityForResult(new Intent(ProductReceiveStockActivity.this, VisionScannerActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            binding.search.setQuery(data.getStringExtra("data"), true);
        }
    }

    private void setupAdapter() {
        receiveStockAdapter = new ReceiveStockAdapter(this, (trxTerimaStockModel, qty) -> {
            productReceiveStockViewModel.updateReceiveQty(
                    trxTerimaStockModel.getNoDo(),
                    trxTerimaStockModel.getKodeArt(),
                    trxTerimaStockModel.getUkuran(),
                    qty);
        });
        binding.recyclerTrxTerima.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerTrxTerima.setAdapter(receiveStockAdapter);

    }

    private void startObserver() {
        productReceiveStockViewModel.getViewState().observe(this, state -> {
            if (state.getCurrentState().equals(ProductReceiveStockViewState.LOADING_STATE.getCurrentState())) {
                binding.setLoading(resourceHelper.getResourceString(R.string.loading));
                binding.setErrorMessage(null);
                return;
            }
            if (state.getCurrentState().equals(ProductReceiveStockViewState.FOUND_STATE.getCurrentState())) {
                binding.setLoading(null);
                receiveStockAdapter.addList(state.getData());
                binding.setErrorMessage(state.getData().isEmpty() ? resourceHelper.getResourceString(R.string.article_not_found) : null);
            }
        });
    }

    private void setupSearchView() {
        binding.search.requestFocus();
        EditText searchEditText = binding.search.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.BLACK);

        binding.search.setMaxWidth(Integer.MAX_VALUE);
        binding.search.setIconifiedByDefault(false);
        binding.search.setIconified(false);
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.trim().isEmpty()) {
                    productReceiveStockViewModel.loadData(getIntent().getStringExtra("NO_DO"));
                    return false;
                }
                if (query.length() < 3) {
                    return false;
                }
                productReceiveStockViewModel.searchData(getIntent().getStringExtra("NO_DO"), query.toLowerCase());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().isEmpty()) {
                    productReceiveStockViewModel.loadData(getIntent().getStringExtra("NO_DO"));
                    return false;
                }
                return false;
            }
        });
    }
}
