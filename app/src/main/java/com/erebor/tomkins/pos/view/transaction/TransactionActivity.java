package com.erebor.tomkins.pos.view.transaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityTransactionBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.scan.VisionScannerActivity;
import com.erebor.tomkins.pos.view.scan.ZynxScannerActivity;
import com.erebor.tomkins.pos.viewmodel.sync.SyncUploadViewModel;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewModel;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewState;
import com.google.android.material.textfield.TextInputEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

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
    SyncUploadViewModel syncUploadViewModel;

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
        syncUploadViewModel = ViewModelProviders.of(this, factory).get(SyncUploadViewModel.class);

        binding.buttonScan.setOnClickListener(v -> {
//            if (true) {
//                transactionViewModel.scanBarcode("89949060800701");
//                return;
//            }
            String scanner = sharedPrefs.getString(getResources().getString(R.string.setting_key_camera), "");
            if (scanner.equals("")) {
                startActivityForResult(new Intent(TransactionActivity.this, VisionScannerActivity.class), 1);
                return;
            }

            if (scanner.equals("zxing")) {
                startActivityForResult(new Intent(TransactionActivity.this, ZynxScannerActivity.class), 1);
                return;
            }

            startActivityForResult(new Intent(TransactionActivity.this, VisionScannerActivity.class), 1);
        });
        binding.buttonConfirm.setOnClickListener(v -> transactionViewModel.saveTransaction((Date) binding.editTransDate.getTag()));

        setupAdapter();
        setupDatePicker();
        startObserver();

        transactionViewModel.scanBarcode(getIntent().getStringExtra("data"));
    }

    private void startObserver() {
        transactionViewModel.getViewState().observe(this, this::onChanged);
    }

    private void setupDatePicker() {
        binding.editTransDate.setTag(Calendar.getInstance().getTime());
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


        datePickerDialog = DatePickerDialog.newInstance((view, year1, monthOfYear, dayOfMonth) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.YEAR, year1);
            calendar1.set(Calendar.MONTH, monthOfYear);
            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            selectedDate = calendar1.getTime();
            binding.editTransDate.setText(dateConverterHelper.toDatetring(calendar1.getTime()));
            binding.editTransDate.setTag(selectedDate);
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

    private void alertDialog(String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, listener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void inputBarcodeDialog(String barcode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setTitle(R.string.transaction_barcode_not_found);
//        builder.setMessage(getResources().getString(R.string.transaction_barcode_input, barcode));
        builder.setMessage(resourceHelper.getResourceString(R.string.transaction_barcode_input, barcode));
        builder.setCancelable(false);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_input_barcode, null);
        TextInputEditText inputBarcode = promptsView.findViewById(R.id.editTransDate);
        inputBarcode.setText(barcode);
        builder.setView(promptsView);
        builder.setPositiveButton(getString(android.R.string.ok), (dialog, which) -> {
            dialog.dismiss();
            transactionViewModel.scanBarcode(inputBarcode.getText().toString());
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onChanged(TransactionViewState transactionViewState) {
        if (transactionViewState.getCurrentState().equals(TransactionViewState.LOADING_STATE.getCurrentState())) {
            binding.setLoading(resourceHelper.getResourceString(R.string.transaction_reading_barcode));
            return;
        }
        if (transactionViewState.getCurrentState().equals(TransactionViewState.FOUND_STATE.getCurrentState())) {
            transactionAdapter.setList(transactionViewState.getData().getListTransaction());
            binding.setTransaction(transactionViewState.getData());
            binding.setLoading(null);
            binding.buttonConfirm.setEnabled(!transactionAdapter.getList().isEmpty());
            syncUploadViewModel.startSyncFull();
            return;
        }

        if (transactionViewState.getCurrentState().equals(TransactionViewState.NOT_FOUND_STATE.getCurrentState())) {
            binding.setLoading(null);
            inputBarcodeDialog(transactionViewState.getData().getBarcode());
            binding.buttonConfirm.setEnabled(!transactionAdapter.getList().isEmpty());
            return;
        }

        if (transactionViewState.getCurrentState().equals(TransactionViewState.ERROR_STATE.getCurrentState())) {
            binding.setLoading(null);
            Toast.makeText(TransactionActivity.this, transactionViewState.getError().getMessage(), Toast.LENGTH_LONG).show();
            return;
        }



        if (transactionViewState.getCurrentState().equals(TransactionViewState.SAVING_STATE.getCurrentState())) {
            binding.setLoading(resourceHelper.getResourceString(R.string.transaction_saving));
            return;
        }
        if (transactionViewState.getCurrentState().equals(TransactionViewState.FAILED_STATE.getCurrentState())) {
            binding.setLoading(null);
            alertDialog(resourceHelper.getResourceString(R.string.transaction_failed), transactionViewState.getError().getMessage(), (dialog, which) -> dialog.dismiss());
            binding.buttonConfirm.setEnabled(!transactionAdapter.getList().isEmpty());
            return;
        }
        if (transactionViewState.getCurrentState().equals(TransactionViewState.SUCCESS_STATE.getCurrentState())) {
            binding.setLoading(null);
//            alertDialog(resourceHelper.getResourceString(R.string.transaction_success), getResources().getString(R.string.transaction_success_message, transactionViewState.getData().getTransactionId()), (dialog, which) -> finish());
            alertDialog(resourceHelper.getResourceString(R.string.transaction_success), resourceHelper.getResourceString(R.string.transaction_success_message, transactionViewState.getData().getTransactionId()), (dialog, which) -> finish());
        }
    }
}
