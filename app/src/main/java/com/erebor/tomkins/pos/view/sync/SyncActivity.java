package com.erebor.tomkins.pos.view.sync;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.databinding.ActivitySyncBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadViewState;

import javax.inject.Inject;

public class SyncActivity extends BaseActivity<ActivitySyncBinding> {
    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    ResourceHelper resourceHelper;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DownloadViewModel downloadViewModel;

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sync;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(binding.toolbar.toolbar);
        binding.setTitle(getResources().getString(R.string.dashboard_data_sync));

        binding.setDownloadArt(new DownloadUiModel("Download Artikel"));
        binding.setDownloadBarcode(new DownloadUiModel("Download Barcode"));
        binding.setDownloadBrand(new DownloadUiModel("Download Brand"));
        binding.setDownloadGender(new DownloadUiModel("Download Gender"));
        binding.setDownloadUkuran(new DownloadUiModel("Download Ukuran"));
        binding.setDownloadStock(new DownloadUiModel("Download Stock"));

        downloadViewModel = ViewModelProviders.of(this, viewModelFactory).get(DownloadViewModel.class);
        observeChanges();

        DownloadUiModel downloadUiModel = new DownloadUiModel();
        downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
        downloadUiModel.setDownloading(false);
        downloadUiModel.setMesssage("click to start download");
        binding.setDownloadSummary(downloadUiModel);
        binding.layoutDownloadInfo.setArrowClick(v -> downloadViewModel.startSyncFull());
        binding.layoutDownloadInfo.setContainerClick(v -> downloadViewModel.startSyncFull());
    }

    private void observeChanges() {
        downloadViewModel.observeChanged();
        downloadViewModel.getDownloadViewState().observe(this, downloadViewState -> {
            if (downloadViewState.getCurrentState() == DownloadViewState.WAITING_STATE.getCurrentState()) {

                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.getDifference(downloadViewState.getLastDownloadTime()));
                binding.setDownloadSummary(downloadUiModel);
                return;
            }

            if (downloadViewState.getCurrentState() == DownloadViewState.LOADING_STATE.getCurrentState()) {

                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(downloadViewState.getMessage());
                downloadUiModel.setDownloading(true);
                downloadUiModel.setLastDownloadTime(resourceHelper.getResourceString(R.string.percent, String.valueOf(downloadViewState.getProgress())));
                downloadUiModel.setProgress(downloadViewState.getProgress());
                binding.setDownloadSummary(downloadUiModel);
                return;
            }

            if (downloadViewState.getCurrentState() == DownloadViewState.SUCCESS_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.last_download));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.getDifference(downloadViewState.getLastDownloadTime()));
                binding.setDownloadSummary(downloadUiModel);
                return;
            }

            if (downloadViewState.getCurrentState() == DownloadViewState.ERROR_STATE.getCurrentState()) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.last_download));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setProgress(downloadViewState.getProgress());
                downloadUiModel.setLastDownloadTime(downloadViewState.getMessage());
                binding.setDownloadSummary(downloadUiModel);

            }
        });
    }
}
