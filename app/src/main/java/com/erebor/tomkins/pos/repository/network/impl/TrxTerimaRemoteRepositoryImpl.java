package com.erebor.tomkins.pos.repository.network.impl;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.repository.network.TrxTerimaRemoteRepository;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.Date;
import java.util.List;

import retrofit2.Call;

public class TrxTerimaRemoteRepositoryImpl implements TrxTerimaRemoteRepository {

    private final SharedPrefs sharedPrefs;
    private final TrxTerimaLocalRepository trxTerimaLocalRepository;
    private final TomkinsService tomkinsService;
    private final DateConverterHelper dateConverterHelper;

    public TrxTerimaRemoteRepositoryImpl(SharedPrefs sharedPrefs, TrxTerimaLocalRepository trxTerimaLocalRepository,
                                         TomkinsService tomkinsService, DateConverterHelper dateConverterHelper) {
        this.sharedPrefs = sharedPrefs;
        this.trxTerimaLocalRepository = trxTerimaLocalRepository;
        this.tomkinsService = tomkinsService;
        this.dateConverterHelper = dateConverterHelper;
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
                        return null;
                    }
                }
            }.fetchData().getData();
        } catch (Exception throwable) {
        }
        return trxTerimaDBModel;
    }
}
