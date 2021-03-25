package com.erebor.tomkins.pos.viewmodel.receive;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDetDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaStockDao;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDetDBModel;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.tools.Logger;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProductReceiveStockViewModel extends BaseViewModel<ProductReceiveStockViewState> {

    private final DateConverterHelper dateConverterHelper;
    private final Logger logger;
    private final TrxTerimaLocalRepository trxTerimaLocalRepository;


    @Inject
    public ProductReceiveStockViewModel(DateConverterHelper dateConverterHelper, Logger logger,
                                        TrxTerimaLocalRepository trxTerimaLocalRepository) {
        this.dateConverterHelper = dateConverterHelper;
        this.logger = logger;
        this.trxTerimaLocalRepository = trxTerimaLocalRepository;
    }



    public void loadData(String noDo) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ProductReceiveStockViewState.LOADING_STATE);
            return trxTerimaLocalRepository.getTrxTerimaStock(noDo);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(data -> {
                            ProductReceiveStockViewState.FOUND_STATE.setData(data);
                            postValue(ProductReceiveStockViewState.FOUND_STATE);
                        },
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            ProductReceiveStockViewState.ERROR_STATE.setError(throwable);
                            postValue(ProductReceiveStockViewState.ERROR_STATE);
                        }));
    }


    public void updateReceiveQty(String noDo, String kodeArt, String ukuran, int qty) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ProductReceiveStockViewState.LOADING_STATE);
            trxTerimaLocalRepository.update(noDo, kodeArt, ukuran, qty);
            return trxTerimaLocalRepository.getTrxTerimaStock(noDo);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(data -> {
                            ProductReceiveStockViewState.FOUND_STATE.setData(data);
                            postValue(ProductReceiveStockViewState.FOUND_STATE);
                        },
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            ProductReceiveStockViewState.ERROR_STATE.setError(throwable);
                            postValue(ProductReceiveStockViewState.ERROR_STATE);
                        }));
    }




}
