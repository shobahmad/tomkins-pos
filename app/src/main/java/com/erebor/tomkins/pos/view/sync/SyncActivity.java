package com.erebor.tomkins.pos.view.sync;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.databinding.ActivitySyncBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDataMasterViewState;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadInfoViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadInfoViewState;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDataMasterViewModel;

import javax.inject.Inject;

public class SyncActivity extends BaseActivity<ActivitySyncBinding> {
    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    ResourceHelper resourceHelper;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    SyncDataMasterViewModel dataSyncViewModel;
    DownloadInfoViewModel downloadInfoViewModel;

    private DownloadInfoAdapter adapter;

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

        adapter = new DownloadInfoAdapter(this);
        binding.recylerDOwnloadInfo.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerDOwnloadInfo.setAdapter(adapter);

        dataSyncViewModel = ViewModelProviders.of(this, viewModelFactory).get(SyncDataMasterViewModel.class);
        downloadInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(DownloadInfoViewModel.class);
        observeChanges();

        DownloadUiModel downloadUiModel = new DownloadUiModel();
        downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
        downloadUiModel.setDownloading(false);
        downloadUiModel.setMesssage("click to start download");
        binding.setDownloadSummary(downloadUiModel);
        binding.layoutDownloadInfo.imageArrowRight.setImageResource(R.drawable.ic_sync);
        binding.layoutDownloadInfo.setArrowClick(v -> dataSyncViewModel.startSyncFull());
        binding.layoutDownloadInfo.setContainerClick(v -> dataSyncViewModel.startSyncFull());

        downloadInfoViewModel.getInfo();
    }

    private void observeChanges() {
        dataSyncViewModel.observeChanged();
        dataSyncViewModel.getViewState().observe(this, dataSyncViewState -> {

            if (dataSyncViewState.getCurrentState().equals(SyncDataMasterViewState.ERROR_STATE.getCurrentState())) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.download_failed));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                downloadUiModel.setLastDownloadTime(dataSyncViewState.getMessage());
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                binding.setDownloadSummary(downloadUiModel);
            }
            if (dataSyncViewState.getCurrentState().equals(SyncDataMasterViewState.SUCCESS_STATE.getCurrentState())) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(dataSyncViewState.getMessage());
                downloadUiModel.setDownloading(true);
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                downloadUiModel.setLastDownloadTime(dateConverterHelper.getDifference(dataSyncViewState.getLastDownloadTime()));
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                binding.setDownloadSummary(downloadUiModel);
                return;
            }

            if (dataSyncViewState.getCurrentState().equals(SyncDataMasterViewState.WAITING_STATE.getCurrentState())) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(getResources().getString(R.string.dashboard_data_sync));
                downloadUiModel.setDownloading(false);
                downloadUiModel.setLastDownloadTime(dateConverterHelper.getDifference(dataSyncViewState.getLastDownloadTime()));
                binding.setDownloadSummary(downloadUiModel);
                return;
            }

            if (dataSyncViewState.getCurrentState().equals(SyncDataMasterViewState.LOADING_STATE.getCurrentState())) {
                DownloadUiModel downloadUiModel = new DownloadUiModel();
                downloadUiModel.setTitle(dataSyncViewState.getMessage());
                downloadUiModel.setMesssage(dataSyncViewState.getMessage());
                downloadUiModel.setDownloading(true);
                downloadUiModel.setProgress(dataSyncViewState.getProgress());
                binding.setDownloadSummary(downloadUiModel);
                return;
            }

        });

        downloadInfoViewModel.getViewState().observe(this, downloadInfoViewState -> {
            if (downloadInfoViewState.getCurrentState().equals(DownloadInfoViewState.LOADING_STATE.getCurrentState())) {
                binding.setLoading(true);
                return;
            }
            if (downloadInfoViewState.getCurrentState().equals(DownloadInfoViewState.SUCCESS_STATE.getCurrentState())) {
                binding.setLoading(false);
                adapter.clearList();
                adapter.addList(downloadInfoViewState.getData());
                return;
            }
        });
    }
}
