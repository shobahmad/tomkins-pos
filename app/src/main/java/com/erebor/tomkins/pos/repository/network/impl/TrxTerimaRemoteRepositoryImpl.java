package com.erebor.tomkins.pos.repository.network.impl;

import android.util.Log;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.repository.network.TrxTerimaRemoteRepository;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import retrofit2.Call;

public class TrxTerimaRemoteRepositoryImpl implements TrxTerimaRemoteRepository {

    private final SharedPrefs sharedPrefs;
    private final TrxTerimaLocalRepository trxTerimaLocalRepository;
    private final TomkinsService tomkinsService;
    private final DateConverterHelper dateConverterHelper;
    private final Logger logger;

    public TrxTerimaRemoteRepositoryImpl(SharedPrefs sharedPrefs, TrxTerimaLocalRepository trxTerimaLocalRepository,
                                         TomkinsService tomkinsService, DateConverterHelper dateConverterHelper, Logger logger) {
        this.sharedPrefs = sharedPrefs;
        this.trxTerimaLocalRepository = trxTerimaLocalRepository;
        this.tomkinsService = tomkinsService;
        this.dateConverterHelper = dateConverterHelper;
        this.logger = logger;
    }

    @Override
    public TrxTerimaDBModel getTrxTerima() {
        TrxTerimaDBModel trxTerimaDBModel = null;
        try {
            trxTerimaDBModel = new NetworkBoundResult<DownloadResponse<TrxTerimaDBModel>>() {
                @Override
                protected Call<RestResponse<DownloadResponse<TrxTerimaDBModel>>> callApiAction() {
                    try {
                        return tomkinsService.getTrxTerima(sharedPrefs.getKodeKonter(),
                                dateConverterHelper.toDateStringParameter(trxTerimaLocalRepository.getLastUpdate()));
                    } catch (Exception throwable) {
                        logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                        return null;
                    }
                }
            }.fetchData().getData();
        } catch (Exception throwable) {
            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
        }
        return trxTerimaDBModel;
    }
}
