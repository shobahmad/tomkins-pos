package com.erebor.tomkins.pos.viewmodel.receive;

import androidx.lifecycle.MutableLiveData;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.mapper.ProductReceiveMapper;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.repository.network.TrxTerimaRemoteRepository;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class ProductReceiveViewModel extends BaseViewModel<ProductReceiveViewState> {

    private final SharedPrefs sharedPrefs;
    private final DateConverterHelper dateConverterHelper;
    private final Logger logger;
    private final TrxTerimaLocalRepository trxTerimaLocalRepository;
    private final TrxTerimaRemoteRepository trxTerimaRemoteRepository;

    public MutableLiveData<Integer> getIncompleteProductReceive() {
        return incompleteProductReceive;
    }

    private final MutableLiveData<Integer> incompleteProductReceive;

    @Inject
    public ProductReceiveViewModel(SharedPrefs sharedPrefs, DateConverterHelper dateConverterHelper,
                                   Logger logger, TrxTerimaLocalRepository trxTerimaLocalRepository,
                                   TrxTerimaRemoteRepository trxTerimaRemoteRepository) {
        this.trxTerimaRemoteRepository = trxTerimaRemoteRepository;
        this.sharedPrefs = sharedPrefs;
        this.dateConverterHelper = dateConverterHelper;
        this.logger = logger;
        this.trxTerimaLocalRepository = trxTerimaLocalRepository;

        incompleteProductReceive = new MutableLiveData<>();
    }

    public void loadDeliveryOrder() {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ProductReceiveViewState.LOADING_STATE);
            ProductReceiveViewState.FOUND_STATE.setData(ProductReceiveMapper.toProductReceive(
                    trxTerimaLocalRepository.getAllTrxTerima(), dateConverterHelper));
            postValue(ProductReceiveViewState.FOUND_STATE);

            TrxTerimaDBModel trxTerimaDBModel = trxTerimaRemoteRepository.getTrxTerima();
            while(trxTerimaDBModel != null) {
                trxTerimaLocalRepository.saveTrxTerima(trxTerimaDBModel);
                ProductReceiveViewState.FOUND_STATE.setData(ProductReceiveMapper.toProductReceive(
                        trxTerimaLocalRepository.getAllTrxTerima(), dateConverterHelper));
                postValue(ProductReceiveViewState.FOUND_STATE);

                trxTerimaDBModel = trxTerimaRemoteRepository.getTrxTerima();
            }
            if (trxTerimaDBModel == null) {
                ProductReceiveViewState.FOUND_STATE.setData(ProductReceiveMapper.toProductReceive(
                        trxTerimaLocalRepository.getAllTrxTerima(), dateConverterHelper));
                postValue(ProductReceiveViewState.FOUND_STATE);
                return true;
            }
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> {},
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            ProductReceiveViewState.ERROR_STATE.setError(throwable);
                            postValue(ProductReceiveViewState.ERROR_STATE);
                        }));
    }


    public void loadSummary() {
        getDisposable().add(Single.fromCallable(trxTerimaLocalRepository::getIncompleteTrxTerima)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(result -> incompleteProductReceive.postValue(result),
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            ProductReceiveViewState.ERROR_STATE.setError(throwable);
                            postValue(ProductReceiveViewState.ERROR_STATE);
                        }));
    }


}
