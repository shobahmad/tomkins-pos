package com.erebor.tomkins.pos.view.dashboard;

import android.os.Bundle;
import android.view.View;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.data.ui.ReportSummaryUiModel;
import com.erebor.tomkins.pos.data.ui.UserUiModel;
import com.erebor.tomkins.pos.databinding.ActivityDashboardBinding;
import com.erebor.tomkins.pos.databinding.ActivityMainBinding;
import com.erebor.tomkins.pos.di.AppComponent;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {

    int downloadState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchDummyData();
    }

    private void fetchDummyData() {

        UserUiModel userUiModel = new UserUiModel();
        userUiModel.setName("Frodo Bagins");
        userUiModel.setPosition("Hobbits");
        binding.setUser(userUiModel);


        binding.layoutDownloadInfo.setOnClickListener(v -> {
            if (downloadState == 0) {
                downloadState = 1;
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setDownloading(true);
                downloadUiModel.setMesssage("Downloading products...");
                downloadUiModel.setProgress(40);
                binding.setDownload(downloadUiModel);

                binding.layoutDownloadInfo.postDelayed(() -> {
                    downloadUiModel.setDownloading(true);
                    downloadUiModel.setMesssage("Downloading price...");
                    downloadUiModel.setProgress(99);
                    binding.setDownload(downloadUiModel);
                }, 1000);

                binding.layoutDownloadInfo.postDelayed(() -> {
                    downloadState = 0;
                    downloadUiModel.setDownloading(false);
                    downloadUiModel.setLastDownloadTime("3 minutes ago");
                    binding.setDownload(downloadUiModel);
                }, 2000);

                return;
            }

            downloadState = 0;
            DownloadUiModel downloadUiModel = new DownloadUiModel();
            downloadUiModel.setDownloading(false);
            downloadUiModel.setLastDownloadTime("3 minutes ago");
            binding.setDownload(downloadUiModel);

        });

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
