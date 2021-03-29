package com.erebor.tomkins.pos.view.receive;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityReceiveStockBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.scan.VisionScannerActivity;
import com.erebor.tomkins.pos.view.scan.ZynxScannerActivity;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewModel;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewState;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class ProductReceiveStockActivity extends BaseActivity<ActivityReceiveStockBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    ResourceHelper resourceHelper;
    @Inject
    DateConverterHelper dateConverterHelper;
    ProductReceiveStockViewModel productReceiveStockViewModel;
    private ReceiveStockAdapter receiveStockAdapter;

    private Date selectedDate = null;
    private DatePickerDialog datePickerDialog;

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
        setupButtons();
        setupDatePicker();
        String noDo = getIntent().getStringExtra("NO_DO");
        productReceiveStockViewModel.loadData(noDo);
        binding.setSubtitle(noDo);
    }


    private void setupDatePicker() {
        binding.editTransDate.setTag(Calendar.getInstance().getTime());
        binding.editTransDate.setText(dateConverterHelper.toDateShortString(Calendar.getInstance().getTime()));
        binding.textTransDate.getEditText().setOnClickListener(v -> showDatePicker());
        binding.textTransDate.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });
        binding.textTransDate.setStartIconOnClickListener(v -> showDatePicker());
        binding.textTransDate.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        int year, month, day;
        Calendar calendar = Calendar.getInstance();
        if (selectedDate != null) {
            calendar.setTime(selectedDate);
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = DatePickerDialog.newInstance((view, year1, monthOfYear, dayOfMonth) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.YEAR, year1);
            calendar1.set(Calendar.MONTH, monthOfYear);
            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            selectedDate = calendar1.getTime();
            binding.editTransDate.setText(dateConverterHelper.toDateShortString(calendar1.getTime()));
            binding.editTransDate.setTag(selectedDate);
        }, year, month, day);
        datePickerDialog.setThemeDark(true);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.orangeSoft));
        datePickerDialog.setTitle(resourceHelper.getResourceString(R.string.transaction_date));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }
    private void setupButtons() {
        binding.buttonScan.setOnClickListener(v -> startScannerActivity());
        binding.buttonSave.setOnClickListener(v ->
                productReceiveStockViewModel.updateDateAndNotes(getIntent().getStringExtra("NO_DO"),
                        selectedDate, binding.editNote.getText().toString()));
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
        productReceiveStockViewModel.getReceiveSummaryUiModelMutableLiveData().observe(this, productReceiveSummaryUiModel -> {
            selectedDate = productReceiveSummaryUiModel.getReceiveDate();
            binding.editTransDate.setText(dateConverterHelper.toDateShortString(productReceiveSummaryUiModel.getReceiveDate()));
            binding.editNote.setText(productReceiveSummaryUiModel.getNote());
        });
        productReceiveStockViewModel.getViewState().observe(this, state -> {
            if (state.getCurrentState().equals(ProductReceiveStockViewState.ERROR_STATE.getCurrentState())) {
                binding.setLoading(null);
                binding.setErrorMessage(state.getError().getMessage());
                return;
            }
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
            if (state.getCurrentState().equals(ProductReceiveStockViewState.UPDATED_STATE.getCurrentState())) {
                transactionResultDialog(true, resourceHelper.getResourceString(R.string.receive_success), (dialog, which) -> {
                    ProductReceiveStockActivity.this.finish();
                });
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


    private void transactionResultDialog(boolean success, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle(success ? R.string.transaction_success : R.string.transaction_failed);
        builder.setMessage(null);
        builder.setCancelable(true);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_transaction_result, null);

        AppCompatTextView textSuccessMessage = promptsView.findViewById(R.id.textSuccessMessage);
        AppCompatTextView textErrorMessage = promptsView.findViewById(R.id.textErrorMessage);
        View layoutError = promptsView.findViewById(R.id.layoutError);
        View layoutSuccess = promptsView.findViewById(R.id.layoutSuccess);

        textSuccessMessage.setText(message);
        textErrorMessage.setText(message);
        layoutError.setVisibility(success ? View.GONE : View.VISIBLE);
        layoutSuccess.setVisibility(success ? View.VISIBLE : View.GONE);

        builder.setView(promptsView);
        builder.setPositiveButton(getString(R.string.ok), listener);



        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

