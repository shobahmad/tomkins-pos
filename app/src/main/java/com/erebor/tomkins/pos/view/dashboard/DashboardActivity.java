package com.erebor.tomkins.pos.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.data.ui.ReportSummaryUiModel;
import com.erebor.tomkins.pos.data.ui.UserUiModel;
import com.erebor.tomkins.pos.databinding.ActivityDashboardBinding;
import com.erebor.tomkins.pos.databinding.ActivityMainBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.view.callback.IItemClick;
import com.erebor.tomkins.pos.view.report.StockActivity;
import com.erebor.tomkins.pos.view.sale.SaleActivity;
import com.erebor.tomkins.pos.view.scan.ScannerActivity;
import com.erebor.tomkins.pos.view.setting.SettingActivity;
import com.erebor.tomkins.pos.view.sync.SyncActivity;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {

    int downloadState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchDummyData();
        binding.buttonScan.setOnClickListener(v -> {
            startActivityForResult(new Intent(DashboardActivity.this, ScannerActivity.class), 1);
        });
        binding.setSettingClick(item -> startActivity(new Intent(DashboardActivity.this, SettingActivity.class)));
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


        binding.layoutDownloadInfo.setArrowClick(v -> {
            startActivity(new Intent(DashboardActivity.this, SyncActivity.class));
        });
        binding.layoutDownloadInfo.setContainerClick(v -> {
            if (downloadState == 0) {
                downloadState = 1;
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
                downloadUiModel.setDownloading(true);
                downloadUiModel.setMesssage("Downloading products...");
                downloadUiModel.setProgress(40);
                binding.setDownload(downloadUiModel);

                binding.layoutDownloadInfo.getRoot().postDelayed(() -> {
                    downloadUiModel.setDownloading(true);
                    downloadUiModel.setMesssage("Downloading price...");
                    downloadUiModel.setProgress(99);
                    binding.setDownload(downloadUiModel);
                }, 1000);

                binding.layoutDownloadInfo.getRoot().postDelayed(() -> {
                    downloadState = 0;
                    downloadUiModel.setDownloading(false);
                    downloadUiModel.setLastDownloadTime("3 minutes ago");
                    binding.setDownload(downloadUiModel);
                }, 2000);

                return;
            }

            downloadState = 0;
            DownloadUiModel downloadUiModel = new DownloadUiModel();
            downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
            downloadUiModel.setDownloading(false);
            downloadUiModel.setLastDownloadTime("3 minutes ago");
            binding.setDownload(downloadUiModel);

        });

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

    }

    @Override
    public int getLayout() {
        return R.layout.activity_dashboard;
    }
}
