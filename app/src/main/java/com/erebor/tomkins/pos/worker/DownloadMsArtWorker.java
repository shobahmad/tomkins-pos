package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class DownloadMsArtWorker extends BaseDownloadWorker<MsArtDBModel> {
    @Inject
    Logger logger;
    @Inject
    MsArtDao msArtDao;
    @Inject
    TomkinsService tomkinsService;

    public DownloadMsArtWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    List<Long> doInsert(List<MsArtDBModel> data) {
        return msArtDao.insertAllReplaceSync(data);
    }

    @Nullable
    @Override
    Date getLastItemUpdate() {
        if (msArtDao.getSyncLatest() == null) {
            return null;
        }
        return msArtDao.getSyncLatest().getLastUpdate();
    }

    @Override
    List<MsArtDBModel> callApi(Date lastUpdate) throws Exception {
        return new NetworkBoundResult<List<MsArtDBModel>>() {
            @Override
            protected Call<RestResponse<List<MsArtDBModel>>> callApiAction() {
                return tomkinsService.getMsArt(lastUpdate);
            }
        }.fetchData();
    }

}
