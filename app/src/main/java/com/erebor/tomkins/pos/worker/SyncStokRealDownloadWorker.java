package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class SyncStokRealDownloadWorker extends BaseSyncDownloadWorker<StokRealDBModel, StokRealDao> {
    @Inject
    Logger logger;
    @Inject
    StokRealDao stokRealDao;
    @Inject
    TomkinsService tomkinsService;
    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    SharedPrefs sharedPrefs;

    public SyncStokRealDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    StokRealDao getDao() {
        return stokRealDao;
    }

    @Override
    SharedPrefs getSharedPrefs() {
        return sharedPrefs;
    }

    @Nullable
    @Override
    Date getLastItemUpdate() {
        if (stokRealDao.getSyncLatest() == null) {
            return null;
        }
        return stokRealDao.getSyncLatest().getLastUpdate();
    }

    @Override
    DownloadResponse<List<StokRealDBModel>> callApi(Date lastUpdate) throws Exception {
        RestResponse restResponse = new NetworkBoundResult<DownloadResponse<List<StokRealDBModel>>>() {
            @Override
            protected Call<RestResponse<DownloadResponse<List<StokRealDBModel>>>> callApiAction() {
                return getDataFromApi(lastUpdate);
            }
        }.fetchBody();



        DownloadResponse<List<StokRealDBModel>> listDownloadResponse = new DownloadResponse<>(null, new ArrayList<>());
        if (restResponse.getResult() != null) {
            DownloadResponse<List<StokRealDBModel>> downloadResponse = (DownloadResponse<List<StokRealDBModel>>) restResponse.getResult();
            listDownloadResponse.getData().addAll(downloadResponse.getData());
        }

        if (restResponse.getResults() != null) {
            for (Object result : restResponse.getResults()) {
                try {
                    DownloadResponse<List<StokRealDBModel>> downloadResponse = (DownloadResponse<List<StokRealDBModel>>) result;
                    listDownloadResponse.getData().addAll(downloadResponse.getData());
                } catch (Exception e) {
                }
            }
        }

        return listDownloadResponse;
    }

    @Override
    Call<RestResponse<DownloadResponse<List<StokRealDBModel>>>> getDataFromApi(Date lastUpdate) {
        return tomkinsService.getStokReal(sharedPrefs.getKodeKonter(),
                dateConverterHelper.toDateTimeStringParameter(lastUpdate));
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }
}
