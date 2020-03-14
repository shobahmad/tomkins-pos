package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;

import java.util.ArrayList;
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
    @Inject
    DateConverterHelper dateConverterHelper;

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
    List<MsArtDBModel> getDataWithLastUpdate(List<MsArtDBModel> data, Date lastUpdate) {
        List<MsArtDBModel> msArtDBModels = new ArrayList<>();
        msArtDBModels.addAll(data);

        for (MsArtDBModel art : msArtDBModels) {
            art.setLastUpdate(lastUpdate);
        }
        return msArtDBModels;
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
    DownloadResponse<List<MsArtDBModel>> callApi(Date lastUpdate) throws Exception {
        return new NetworkBoundResult<DownloadResponse<List<MsArtDBModel>>>() {
            @Override
            protected Call<RestResponse<DownloadResponse<List<MsArtDBModel>>>> callApiAction() {
                return tomkinsService.getMsArt(dateConverterHelper.toDateTimeString(lastUpdate));
            }
        }.fetchData();
    }

}
