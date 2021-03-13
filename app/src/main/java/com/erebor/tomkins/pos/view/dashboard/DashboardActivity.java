package com.erebor.tomkins.pos.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.databinding.ActivityDashboardBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.login.LoginActivity;
import com.erebor.tomkins.pos.view.report.StockActivity;
import com.erebor.tomkins.pos.view.setting.SettingActivity;
import com.erebor.tomkins.pos.view.sync.DownloadInfoAdapter;
import com.erebor.tomkins.pos.view.transaction.TransactionActivity;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewModel;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewState;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadInfoViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadInfoViewState;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDownloadViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDownloadViewState;
import com.erebor.tomkins.pos.viewmodel.sync.SyncUploadStockViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncUploadTrxViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncUploadViewState;
import com.erebor.tomkins.pos.worker.SyncUploadStockWorker;

import javax.inject.Inject;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {

    int downloadState = 0;

    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    ResourceHelper resourceHelper;

    SyncDownloadViewModel dataSyncViewModel;
    SyncUploadTrxViewModel syncUploadTrxViewModel;
    SyncUploadStockViewModel syncUploadStockViewModel;
    DownloadInfoViewModel downloadInfoViewModel;
    LoginViewModel loginViewModel;

    private DownloadInfoAdapter downloadInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(SyncDownloadViewModel.class);
        syncUploadTrxViewModel = ViewModelProviders.of(this, viewModelFactory).get(SyncUploadTrxViewModel.class);
        syncUploadStockViewModel = ViewModelProviders.of(this, viewModelFactory).get(SyncUploadStockViewModel.class);
        downloadInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(DownloadInfoViewModel.class);
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        observeChanges();

        binding.layoutReport.setOnClickListener(v -> startReportMenu());
        binding.imageArrowRight.setOnClickListener(v -> startReportMenu());
        binding.buttonScan.setOnClickListener(v -> {
            startSaleActivity(null);
        });
        binding.setSettingClick(item -> startActivity(new Intent(DashboardActivity.this, SettingActivity.class)));

        downloadInfoAdapter = new DownloadInfoAdapter(this);
        binding.snippetSyncSummary.recylerDownloadInfo.setLayoutManager(new LinearLayoutManager(this));
        binding.snippetSyncSummary.recylerDownloadInfo.setAdapter(downloadInfoAdapter);

        binding.layoutDownloadInfo.setArrowClick(v -> dataSyncViewModel.observeChanged());
        binding.layoutDownloadInfo.setContainerClick(v -> dataSyncViewModel.observeChanged());
    }

    private void startReportMenu() {
        Intent intent = new Intent(DashboardActivity.this, StockActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dataSyncViewModel.observeChanged();
        loginViewModel.checkSession();
        downloadInfoViewModel.getInfo();
    }

    private void observeChanges() {
        dataSyncViewModel.observeChanged();
        dataSyncViewModel.getViewState().observe(this, dataSyncViewState -> {
            if (dataSyncViewState.getCurrentState().equals(SyncDownloadViewState.ERROR_STATE.getCurrentState())) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.download_failed));
                downloadUiModel.setDownloading(true);
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                downloadUiModel.setLastDownloadTime(dataSyncViewState.getMessage());
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                binding.setDownload(downloadUiModel);
                binding.layoutDownloadInfo.textDownloadInfo.setTextColor(getResources().getColor(R.color.orangeSoft));
                binding.layoutDownloadInfo.textLabelLastDownload.setTextColor(getResources().getColor(R.color.orangeSoft));
            }
            if (dataSyncViewState.getCurrentState().equals(SyncDownloadViewState.WAITING_STATE.getCurrentState())) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.toDateTimeDisplayString(dataSyncViewState.getLastDownloadTime()));
                binding.setDownload(downloadUiModel);
                binding.layoutDownloadInfo.textDownloadInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.layoutDownloadInfo.textLabelLastDownload.setTextColor(getResources().getColor(R.color.colorPrimary));
                return;
            }

            if (dataSyncViewState.getCurrentState().equals(SyncDownloadViewState.LOADING_STATE.getCurrentState())) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(dataSyncViewState.getMessage());
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                downloadUiModel.setDownloading(true);
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                binding.setDownload(downloadUiModel);
                binding.layoutDownloadInfo.textDownloadInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.layoutDownloadInfo.textLabelLastDownload.setTextColor(getResources().getColor(R.color.colorPrimary));
                return;
            }

            if (dataSyncViewState.getCurrentState().equals(SyncDownloadViewState.SUCCESS_STATE.getCurrentState())) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.last_download));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.toDateTimeDisplayString(dataSyncViewState.getLastDownloadTime()));
                binding.setDownload(downloadUiModel);
                binding.layoutDownloadInfo.textDownloadInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.layoutDownloadInfo.textLabelLastDownload.setTextColor(getResources().getColor(R.color.colorPrimary));
                return;
            }
        });

        loginViewModel.getViewState().observe(this, loginViewState -> {
            if (loginViewState.getCurrentState().equals(LoginViewState.LOGIN_VALID_STATE.getCurrentState())) {
                binding.setUser(loginViewState.getData());
                return;
            }

            if (loginViewState.getCurrentState().equals(LoginViewState.LOGOUT_VALID_STATE.getCurrentState())) {
                finish();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                return;
            }
            if (loginViewState.getCurrentState().equals(LoginViewState.ERROR_STATE.getCurrentState())) {
                Toast.makeText(DashboardActivity.this, loginViewState.getError().getMessage(), Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            }
        });

        downloadInfoViewModel.getViewState().observe(this, downloadInfoViewState -> {
            if (downloadInfoViewState.getCurrentState().equals(DownloadInfoViewState.LOADING_STATE.getCurrentState())) {
                binding.snippetSyncSummary.setLoading(true);
                return;
            }
            if (downloadInfoViewState.getCurrentState().equals(DownloadInfoViewState.SUCCESS_STATE.getCurrentState())) {
                binding.snippetSyncSummary.setLoading(false);
                downloadInfoAdapter.clearList();
                downloadInfoAdapter.addList(downloadInfoViewState.getData());
                return;
            }
        });

        syncUploadStockViewModel.observeChanged();

        syncUploadTrxViewModel.observeChanged();
        syncUploadTrxViewModel.getViewState().observe(this, syncUploadViewState -> {
            if (syncUploadViewState.getCurrentState().equals(SyncUploadViewState.STATE_WAITING)) {
                binding.setUploadInfo(resourceHelper.getResourceString(R.string.dashboard_pending));
                binding.setUploading(false);
                binding.textLabelPending.setTextColor(getResources().getColor(R.color.colorPrimary));
                return;
            }
            if (syncUploadViewState.getCurrentState().equals(SyncUploadViewState.STATE_LOADING)) {
                binding.setUploadInfo(resourceHelper.getResourceString(R.string.dashboard_uploading));
                binding.setUploading(true);
                binding.textLabelPending.setTextColor(getResources().getColor(R.color.colorPrimary));
                return;
            }
            if (syncUploadViewState.getCurrentState().equals(SyncUploadViewState.STATE_FAILED)) {
                binding.setUploadInfo(syncUploadViewState.getError().getMessage());
                binding.setUploading(false);
                binding.textLabelPending.setTextColor(getResources().getColor(R.color.orangeSoft));
                return;
            }
            if (syncUploadViewState.getCurrentState().equals(SyncUploadViewState.STATE_SUCCESS)) {
                binding.setUploadInfo(resourceHelper.getResourceString(R.string.dashboard_pending));
                binding.setUploading(false);
                binding.textLabelPending.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        syncUploadTrxViewModel.getPendingCountLiveData().observe(this, pendingCount -> {
            binding.setUploadCount(pendingCount);
        });
    }

    private void startSaleActivity(String barcode) {
        Intent intent = new Intent(DashboardActivity.this, TransactionActivity.class);
        if (barcode != null) intent.putExtra("data", barcode);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String qrcode = data.getStringExtra("data");
            fetchBarcode(qrcode);
            return;
        }

        if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            startSaleActivity(null);
        }
    }

    private void fetchBarcode(String qrcode) {
        startSaleActivity(qrcode);
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
