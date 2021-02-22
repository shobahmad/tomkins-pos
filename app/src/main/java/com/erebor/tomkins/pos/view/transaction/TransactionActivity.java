package com.erebor.tomkins.pos.view.transaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
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
import com.erebor.tomkins.pos.view.article.ArticleActivity;
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

        binding.buttonAdd.setOnClickListener(v -> startSearchArticle());
        binding.buttonScan.setOnClickListener(v -> {
//            if (true) {
//                transactionViewModel.scanBarcode("89949060800701");
//                return;
//            }
            startScannerActivity();
        });
        binding.buttonEmptyAdd.setOnClickListener(v -> startSearchArticle());
        binding.buttonEmptyScan.setOnClickListener(v -> {
//            if (true) {
//                transactionViewModel.scanBarcode("89949060800701");
//                return;
//            }
            startScannerActivity();
        });

        setupAdapter();
        setupDatePicker();
        startObserver();

        binding.setEmpty(true);
        transactionViewModel.loadTransaction(selectedDate);
    }

    private void startScannerActivity() {
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
    }

    private void startObserver() {
        transactionViewModel.getViewState().observe(this, this::onChanged);
    }

    private void setupDatePicker() {
        binding.editTransDate.setTag(Calendar.getInstance().getTime());
        binding.editTransDate.setText(dateConverterHelper.toDatetring(Calendar.getInstance().getTime()));
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
            binding.editTransDate.setText(dateConverterHelper.toDatetring(calendar1.getTime()));
            binding.editTransDate.setTag(selectedDate);
            transactionViewModel.loadTransaction(selectedDate);
        }, year, month, day);
        datePickerDialog.setThemeDark(true);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.orangeSoft));
        datePickerDialog.setTitle(resourceHelper.getResourceString(R.string.transaction_date));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    private void setupAdapter() {
        transactionAdapter = new TransactionAdapter(this);
        binding.recyclerTransaction.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerTransaction.setAdapter(transactionAdapter);
    }

    @Override
    public void onBackPressed() {
        confirmationDialog();
    }


    private void confirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle(resourceHelper.getResourceString(R.string.transaction_cancel));
        builder.setMessage(resourceHelper.getResourceString(R.string.transaction_cancel_confirm));

        builder.setPositiveButton(R.string.yes, (dialog, which) -> transactionViewModel.reset());
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            startSearchArticle(data.getStringExtra("data"));
            return;
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            String barcode = data.getStringExtra("barcode");
            boolean isSale = data.getBooleanExtra("is_sale", true);
            String note = data.getStringExtra("note");
            String discount = cleanPriceString(data.getStringExtra("discount"));
            String price = cleanPriceString(data.getStringExtra("price"));

            transactionViewModel.saveTransaction((Date) binding.editTransDate.getTag(), barcode, isSale, note, discount, price);
            return;
        }
        binding.setEmpty(transactionAdapter.getList() == null || transactionAdapter.getList().isEmpty());
    }

    private String cleanPriceString(String price) {
        return price.trim().replaceAll("[$,.]", "")
                .replaceAll(" ", "")
                .replaceAll("IDR", "")
                .replaceAll("Rp", "")
                .replaceAll("%", "")
                ;
    }

    private void alertDialog(String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, listener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void inputBarcodeDialog(String barcode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle(barcode == null ? R.string.transaction_barcode_input_manually : R.string.transaction_barcode_not_found);
        builder.setMessage(barcode == null ? "" : resourceHelper.getResourceString(R.string.transaction_barcode_input, barcode));
        builder.setCancelable(true);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_input_barcode, null);
        TextInputEditText inputBarcode = promptsView.findViewById(R.id.editBarcode);
        inputBarcode.setText(barcode);
        builder.setView(promptsView);
        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            dialog.dismiss();
            String newbarcode = inputBarcode.getEditableText().toString();
            startSearchArticle(newbarcode);
        });
        builder.setNegativeButton(getString(R.string.cancel), ((dialog, which) -> {
            dialog.dismiss();
        }));


        AlertDialog dialog = builder.create();
        dialog.show();

        showSoftKeyboard(inputBarcode);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void onChanged(TransactionViewState transactionViewState) {
        if (transactionViewState.getCurrentState().equals(TransactionViewState.LOADING_STATE.getCurrentState())) {
            binding.setLoading(resourceHelper.getResourceString(R.string.transaction_reading_barcode));
            return;
        }
        if (transactionViewState.getCurrentState().equals(TransactionViewState.LOADED_STATE.getCurrentState())) {
            transactionAdapter.setList(transactionViewState.getData().getListTransaction());
            binding.setTransaction(transactionViewState.getData());
            binding.setLoading(null);
            binding.setEmpty(transactionAdapter.getList() == null || transactionAdapter.getList().isEmpty());
            return;
        }
        if (transactionViewState.getCurrentState().equals(TransactionViewState.FOUND_STATE.getCurrentState())) {
            transactionAdapter.setList(transactionViewState.getData().getListTransaction());
            binding.setTransaction(transactionViewState.getData());
            binding.setLoading(null);
            binding.setEmpty(transactionAdapter.getList() == null || transactionAdapter.getList().isEmpty());
            syncUploadViewModel.startSyncFull();
            return;
        }

        if (transactionViewState.getCurrentState().equals(TransactionViewState.NOT_FOUND_STATE.getCurrentState())) {
            binding.setLoading(null);
            inputBarcodeDialog(transactionViewState.getData().getBarcode());
            binding.setEmpty(transactionAdapter.getList() == null || transactionAdapter.getList().isEmpty());
            return;
        }

        if (transactionViewState.getCurrentState().equals(TransactionViewState.ERROR_STATE.getCurrentState())) {
            binding.setLoading(null);
            binding.setEmpty(transactionAdapter.getList() == null || transactionAdapter.getList().isEmpty());
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
            return;
        }
        if (transactionViewState.getCurrentState().equals(TransactionViewState.SUCCESS_STATE.getCurrentState())) {
            binding.setLoading(null);
            alertDialog(resourceHelper.getResourceString(R.string.transaction_success), resourceHelper.getResourceString(R.string.transaction_success_message, transactionViewState.getData().getTransactionId()), (dialog, which) -> {
                transactionViewModel.loadTransaction(selectedDate);
            });
            return;
        }
        if (transactionViewState.getCurrentState().equals(TransactionViewState.RESET_STATE.getCurrentState())) {
            finish();
        }
    }

    private void startSearchArticle() {
        startSearchArticle(null);
    }
    private void startSearchArticle(String barcode) {
        Intent intent = new Intent(this, ArticleActivity.class);
        if (barcode != null) {
            intent.putExtra("barcode", barcode);
        }
        startActivityForResult(intent, 2);
    }

}
