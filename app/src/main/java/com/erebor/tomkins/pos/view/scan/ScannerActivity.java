package com.erebor.tomkins.pos.view.scan;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.databinding.ActivityScannerBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.CheckPermissionHelper;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends BaseActivity<ActivityScannerBinding> implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_scanner;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(binding.toolbar);
        mScannerView = new ZXingScannerView(this);
        binding.contentFrame.addView(mScannerView);

        CheckPermissionHelper checkPermissionHelper = new CheckPermissionHelper(this);
        if (!checkPermissionHelper.checkCameraPermission())
            checkPermissionHelper.showCameraPermission(1);

        binding.layoutFlashMode.setOnClickListener(v -> {
            binding.setFlash(binding.getFlash() != null && !binding.getFlash());
            mScannerView.setFlash(!mScannerView.getFlash());
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            onResume();
        }
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        intent.putExtra("data", result.getText());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mScannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mScannerView.stopCamera();
    }
}
