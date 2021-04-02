package com.erebor.tomkins.pos.viewmodel.report;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.repository.local.StockReportLocalRepository;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewState;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class StockReportViewModel extends BaseViewModel<StockReportViewState> {

    private final StockReportLocalRepository stockReportLocalRepository;
    private final Logger logger;

    @Inject
    public StockReportViewModel(StockReportLocalRepository stockReportLocalRepository, Logger logger) {
        this.stockReportLocalRepository = stockReportLocalRepository;
        this.logger = logger;
    }


    public void getStockLatest() {
        setValue(StockReportViewState.LOADING_STATE);

        getDisposable().add(Single.fromCallable(() -> {
            return stockReportLocalRepository.getStockLatest();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(result ->  {
                            if (result == null || result.isEmpty()) {
                                postValue(StockReportViewState.NOT_FOUND_STATE);
                                return;
                            }
                            StockReportViewState.FOUND_STATE.setData(result);
                            postValue(StockReportViewState.FOUND_STATE);
                        },
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            StockReportViewState.ERROR_STATE.setError(throwable);
                            postValue(StockReportViewState.ERROR_STATE);
                        }));
    }
    public void getStock(String kodeArt, String ukuran, String gender) {
        setValue(StockReportViewState.LOADING_STATE);

        getDisposable().add(Single.fromCallable(() -> stockReportLocalRepository.getStock(kodeArt, ukuran, gender))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(result ->  {
                            if (result == null || result.isEmpty()) {
                                postValue(StockReportViewState.NOT_FOUND_STATE);
                                return;
                            }
                            StockReportViewState.FOUND_STATE.setData(result);
                            postValue(StockReportViewState.FOUND_STATE);
                        },
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            StockReportViewState.ERROR_STATE.setError(throwable);
                            postValue(StockReportViewState.ERROR_STATE);
                        }));
    }

}
