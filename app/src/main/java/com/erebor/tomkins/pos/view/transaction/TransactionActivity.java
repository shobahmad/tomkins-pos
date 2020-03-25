package com.erebor.tomkins.pos.view.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.TransactionDetailUiModel;
import com.erebor.tomkins.pos.databinding.ActivityTransactionBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.scan.VisionScannerActivity;
import com.erebor.tomkins.pos.view.scan.ZynxScannerActivity;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewModel;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewState;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class TransactionActivity extends BaseActivity<ActivityTransactionBinding> {
    @Inject
    ResourceHelper resourceHelper;
    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    SharedPrefs sharedPrefs;

    TransactionViewModel transactionViewModel;

    private Date selectedDate = null;

    private TransactionAdapter transactionAdapter;
    private DatePickerDialog datePickerDialog;
    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_transaction;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(binding.toolbar.toolbar);
        binding.toolbar.setTitle(resourceHelper.getResourceString(R.string.transaction));
        transactionViewModel = ViewModelProviders.of(this, factory).get(TransactionViewModel.class);

        binding.buttonScan.setOnClickListener(v -> {
            if (true) {
                transactionViewModel.scanBarcode("8994906080070");
                return;
            }
            String scanner = sharedPrefs.getString(getResources().getString(R.string.setting_key_camera), "");
            if (scanner.equals("")) {
                startActivityForResult(new Intent(TransactionActivity.this, ZynxScannerActivity.class), 1);
                return;
            }

            if (scanner.equals("zxing")) {
                startActivityForResult(new Intent(TransactionActivity.this, ZynxScannerActivity.class), 1);
                return;
            }

            startActivityForResult(new Intent(TransactionActivity.this, VisionScannerActivity.class), 1);
        });

        setupAdapter();
        setupDatePicker();
        startObserver();

        transactionViewModel.scanBarcode(getIntent().getStringExtra("data"));
    }

    private void startObserver() {
        transactionViewModel.getViewState().observe(this, transactionViewState -> {
            if (transactionViewState.getCurrentState().equals(TransactionViewState.FOUND_STATE.getCurrentState())) {
                transactionAdapter.setList(transactionViewState.getData().getListTransaction());
                binding.setTransaction(transactionViewState.getData());
                return;
            }

            if (transactionViewState.getCurrentState().equals(TransactionViewState.NOT_FOUND_STATE.getCurrentState())) {
                Toast.makeText(TransactionActivity.this, "barcode not found!", Toast.LENGTH_LONG).show();
                return;
            }

            if (transactionViewState.getCurrentState().equals(TransactionViewState.ERROR_STATE.getCurrentState())) {
                Toast.makeText(TransactionActivity.this, transactionViewState.getError().getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void setupDatePicker() {
        binding.editTransDate.setText(dateConverterHelper.toDatetring(Calendar.getInstance().getTime()));
        binding.textTransDate.getEditText().setOnClickListener(v -> showDatePicker());
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


        datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDate = calendar.getTime();
                binding.editTransDate.setText(dateConverterHelper.toDatetring(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.setThemeDark(true);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.setTitle(resourceHelper.getResourceString(R.string.transaction_date));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    private void setupAdapter() {
        transactionAdapter = new TransactionAdapter(this);
        transactionAdapter.setItemUpdatedListener(new TransactionAdapter.ItemUpdatedListener() {
            @Override
            public void qtyUpdate(String barcode, int qty) {
                transactionViewModel.updateQuantity(barcode, qty);
            }

            @Override
            public void noteUpdate(String barcode, String note) {
                transactionViewModel.updateNote(barcode, note);
            }
        });
        binding.recyclerTransaction.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerTransaction.setAdapter(transactionAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            transactionViewModel.scanBarcode(data.getStringExtra("data"));
        }
    }
}
