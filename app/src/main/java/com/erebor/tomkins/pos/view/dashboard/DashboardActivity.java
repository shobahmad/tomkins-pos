package com.erebor.tomkins.pos.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.data.ui.ReportSummaryUiModel;
import com.erebor.tomkins.pos.data.ui.UserUiModel;
import com.erebor.tomkins.pos.databinding.ActivityDashboardBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.login.LoginActivity;
import com.erebor.tomkins.pos.view.report.StockActivity;
import com.erebor.tomkins.pos.view.sale.SaleActivity;
import com.erebor.tomkins.pos.view.scan.VisionScannerActivity;
import com.erebor.tomkins.pos.view.scan.ZynxScannerActivity;
import com.erebor.tomkins.pos.view.setting.SettingActivity;
import com.erebor.tomkins.pos.view.sync.SyncActivity;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewModel;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewState;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDataMasterViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDataMasterViewState;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {

    int downloadState = 0;

    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    SyncDataMasterViewModel dataSyncViewModel;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(SyncDataMasterViewModel.class);
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        observeChanges();

        loginViewModel.checkSession();
        dataSyncViewModel.observeChanged();
        fetchDummyData();
        binding.buttonScan.setOnClickListener(v -> {

            String scanner = sharedPrefs.getString(getResources().getString(R.string.setting_key_camera), "");
            if (scanner.equals("")) {
                startActivityForResult(new Intent(DashboardActivity.this, ZynxScannerActivity.class), 1);
                return;
            }

            if (scanner.equals("zxing")) {
                startActivityForResult(new Intent(DashboardActivity.this, ZynxScannerActivity.class), 1);
                return;
            }

            startActivityForResult(new Intent(DashboardActivity.this, VisionScannerActivity.class), 1);
        });
        binding.setSettingClick(item -> startActivity(new Intent(DashboardActivity.this, SettingActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        dataSyncViewModel.observeChanged();
    }

    private void observeChanges() {
        dataSyncViewModel.getViewState().observe(this, dataSyncViewState -> {
            if (dataSyncViewState.getCurrentState() == SyncDataMasterViewState.WAITING_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.getDifference(dataSyncViewState.getLastDownloadTime()));
                binding.setDownload(downloadUiModel);
                return;
            }

            if (dataSyncViewState.getCurrentState() == SyncDataMasterViewState.LOADING_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(dataSyncViewState.getMessage());
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                downloadUiModel.setDownloading(true);
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                binding.setDownload(downloadUiModel);
                return;
            }

            if (dataSyncViewState.getCurrentState() == SyncDataMasterViewState.SUCCESS_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.last_download));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.getDifference(dataSyncViewState.getLastDownloadTime()));
                binding.setDownload(downloadUiModel);
                return;
            }

            if (dataSyncViewState.getCurrentState() == SyncDataMasterViewState.ERROR_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.download_failed));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                downloadUiModel.setLastDownloadTime(dataSyncViewState.getMessage());
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                binding.setDownload(downloadUiModel);
            }
        });

        loginViewModel.getViewState().observe(this, loginViewState -> {
            if (loginViewState.getCurrentState() == LoginViewState.LOGIN_VALID_STATE.getCurrentState()) {
                binding.setUser(loginViewState.getData());
                return;
            }

            if (loginViewState.getCurrentState() == LoginViewState.ERROR_STATE.getCurrentState()) {
                Toast.makeText(DashboardActivity.this, loginViewState.getError().getMessage(), Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                return;
            }
        });
    }

    private void startSaleActivity(String productId) {
        Intent intent = new Intent(DashboardActivity.this, SaleActivity.class);
        intent.putExtra("data", productId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String qrcode = data.getStringExtra("data");
            fetchBarcode(qrcode);
        }
    }

    private void fetchBarcode(String qrcode) {
        startSaleActivity(qrcode);
    }

    private void fetchDummyData() {
        binding.layoutDownloadInfo.setArrowClick(v -> startActivity(new Intent(DashboardActivity.this, SyncActivity.class)));
        binding.layoutDownloadInfo.setContainerClick(v -> startActivity(new Intent(DashboardActivity.this, SyncActivity.class)));

        binding.setReportDetailClick(v -> {
            Intent intent = new Intent(DashboardActivity.this, StockActivity.class);
            startActivity(intent);
        });

        downloadState = 0;
        DownloadUiModel downloadUiModel = new DownloadUiModel();
        downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
        downloadUiModel.setDownloading(false);
        downloadUiModel.setLastDownloadTime("3 minutes ago");
        binding.setDownload(downloadUiModel);

        ReportSummaryUiModel reportSummaryUiModel = new ReportSummaryUiModel();
        reportSummaryUiModel.setStockTotal(120);
        reportSummaryUiModel.setStockIncoming(100);
        reportSummaryUiModel.setStockOutgoing(82);
        binding.setSummary(reportSummaryUiModel);
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_dashboard;
    }
}
