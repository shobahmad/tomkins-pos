package com.erebor.tomkins.pos.viewmodel.receive;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.mapper.ProductReceiveMapper;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class ProductReceiveViewModel extends BaseViewModel<ProductReceiveViewState> {

    private final TomkinsService tomkinsService;
    private final SharedPrefs sharedPrefs;
    private final DateConverterHelper dateConverterHelper;
    private final Logger logger;
    private final TrxTerimaLocalRepository trxTerimaLocalRepository;

    @Inject
    public ProductReceiveViewModel(TomkinsService tomkinsService, SharedPrefs sharedPrefs, DateConverterHelper dateConverterHelper, Logger logger, TrxTerimaLocalRepository trxTerimaLocalRepository) {
        this.tomkinsService = tomkinsService;
        this.sharedPrefs = sharedPrefs;
        this.dateConverterHelper = dateConverterHelper;
        this.logger = logger;
        this.trxTerimaLocalRepository = trxTerimaLocalRepository;
    }

    public void loadDeliveryOrder() {
        getDisposable().add(Single.fromCallable(() -> {
            TrxTerimaDBModel trxTerimaDBModel = new NetworkBoundResult<TrxTerimaDBModel>() {
                @Override
                protected Call<RestResponse<TrxTerimaDBModel>> callApiAction() {
                    try {
                        return tomkinsService.getTrxTerima(sharedPrefs.getKodeKonter(),
                                dateConverterHelper.toDateTimeStringParameter(trxTerimaLocalRepository.getLastUpdate()));
                    } catch (Exception throwable) {
                        logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                        return null;
                    }
                }
            }.fetchData();
            if (trxTerimaDBModel == null) {
                ProductReceiveViewState.FOUND_STATE.setData(ProductReceiveMapper.toProductReceive(trxTerimaLocalRepository.getAllTrxTerima()));
                return ProductReceiveViewState.FOUND_STATE;
            }
            trxTerimaLocalRepository.saveTrxTerima(trxTerimaDBModel);
            ProductReceiveViewState.FOUND_STATE.setData(ProductReceiveMapper.toProductReceive(trxTerimaLocalRepository.getAllTrxTerima()));
            return ProductReceiveViewState.FOUND_STATE;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> postValue(state),
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            ProductReceiveViewState.ERROR_STATE.setError(throwable);
                            postValue(ProductReceiveViewState.ERROR_STATE);
                        }));
    }


}
