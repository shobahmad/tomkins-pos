package com.erebor.tomkins.pos.view.dashboard;

import android.content.Intent;
import android.os.Bundle;

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
import com.erebor.tomkins.pos.view.report.StockActivity;
import com.erebor.tomkins.pos.view.sale.SaleActivity;
import com.erebor.tomkins.pos.view.scan.VisionScannerActivity;
import com.erebor.tomkins.pos.view.scan.ZynxScannerActivity;
import com.erebor.tomkins.pos.view.setting.SettingActivity;
import com.erebor.tomkins.pos.view.sync.SyncActivity;
import com.erebor.tomkins.pos.viewmodel.sync.DataSyncViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.DataSyncViewState;

import javax.inject.Inject;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {

    int downloadState = 0;

    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DataSyncViewModel dataSyncViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(DataSyncViewModel.class);
        observeChanges();

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

    private void observeChanges() {
        dataSyncViewModel.observeChanged();
        dataSyncViewModel.getDownloadViewState().observe(this, dataSyncViewState -> {
            if (dataSyncViewState.getCurrentState() == DataSyncViewState.WAITING_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.getDifference(dataSyncViewState.getLastDownloadTime()));
                binding.setDownload(downloadUiModel);
                return;
            }

            if (dataSyncViewState.getCurrentState() == DataSyncViewState.LOADING_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(dataSyncViewState.getMessage());
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                downloadUiModel.setDownloading(true);
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                binding.setDownload(downloadUiModel);
                return;
            }

            if (dataSyncViewState.getCurrentState() == DataSyncViewState.SUCCESS_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.last_download));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.getDifference(dataSyncViewState.getLastDownloadTime()));
                binding.setDownload(downloadUiModel);
                return;
            }

            if (dataSyncViewState.getCurrentState() == DataSyncViewState.ERROR_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.download_failed));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                downloadUiModel.setLastDownloadTime(dataSyncViewState.getMessage());
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                binding.setDownload(downloadUiModel);
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

        UserUiModel userUiModel = new UserUiModel();
        userUiModel.setName("Frodo Bagins");
        userUiModel.setPosition("Hobbits");
        binding.setUser(userUiModel);


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
