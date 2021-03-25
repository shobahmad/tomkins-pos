package com.erebor.tomkins.pos.viewmodel.receive;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaStockDao;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.tools.Logger;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProductReceiveStockViewModel extends BaseViewModel<ProductReceiveStockViewState> {

    private final DateConverterHelper dateConverterHelper;
    private final Logger logger;
    private final TrxTerimaStockDao trxTerimaStockDao;

    @Inject
    public ProductReceiveStockViewModel(DateConverterHelper dateConverterHelper, Logger logger, TrxTerimaStockDao trxTerimaStockDao) {
        this.dateConverterHelper = dateConverterHelper;
        this.logger = logger;
        this.trxTerimaStockDao = trxTerimaStockDao;
    }



    public void loadData(String noDo) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ProductReceiveStockViewState.LOADING_STATE);
            return trxTerimaStockDao.getTrxTerimaStock(noDo);
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
