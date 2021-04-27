package com.erebor.tomkins.pos.viewmodel.receive;

import androidx.lifecycle.MutableLiveData;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;
import com.erebor.tomkins.pos.data.ui.ProductReceiveSummaryUiModel;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.tools.Logger;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProductReceiveStockViewModel extends BaseViewModel<ProductReceiveStockViewState> {

    private final DateConverterHelper dateConverterHelper;
    private final Logger logger;
    private final TrxTerimaLocalRepository trxTerimaLocalRepository;
    private final ResourceHelper resourceHelper;


    private final MutableLiveData<ProductReceiveSummaryUiModel> receiveSummaryUiModelMutableLiveData;

    public MutableLiveData<ProductReceiveSummaryUiModel> getReceiveSummaryUiModelMutableLiveData() {
        return receiveSummaryUiModelMutableLiveData;
    }

    @Inject
    public ProductReceiveStockViewModel(DateConverterHelper dateConverterHelper, Logger logger,
                                        TrxTerimaLocalRepository trxTerimaLocalRepository, ResourceHelper resourceHelper) {
        this.dateConverterHelper = dateConverterHelper;
        this.logger = logger;
        this.trxTerimaLocalRepository = trxTerimaLocalRepository;
        this.resourceHelper = resourceHelper;

        receiveSummaryUiModelMutableLiveData = new MutableLiveData<>();
    }



    public void loadData(String noDo) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ProductReceiveStockViewState.LOADING_STATE);

            receiveSummaryUiModelMutableLiveData.postValue(trxTerimaLocalRepository.getTrxTerimaSummary(noDo));
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
    public void searchData(String noDo, String query) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ProductReceiveStockViewState.LOADING_STATE);
            return trxTerimaLocalRepository.searchTrxTerimaStock(noDo, query);
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

    public void receiveBarcode(String noDo, String barcode) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ProductReceiveStockViewState.LOADING_STATE);
            List<TrxTerimaStockModel> stockModels = trxTerimaLocalRepository.searchTrxTerimaStock(noDo, barcode);
            if (stockModels.size() != 1) {
                ProductReceiveStockViewState.UPDATE_FAILED_STATE.setData(trxTerimaLocalRepository.getTrxTerimaStock(noDo));
                throw new Exception(resourceHelper.getResourceString(R.string.transaction_barcode_not_found));
            }
            TrxTerimaStockModel stockModel = stockModels.get(0);
            if (stockModel.getQtyTerima().intValue() == stockModel.getQtyKirim().intValue()) {
                ProductReceiveStockViewState.UPDATE_FAILED_STATE.setData(trxTerimaLocalRepository.getTrxTerimaStock(noDo));
                throw new Exception(resourceHelper.getResourceString(R.string.transaction_receive_completed));
            }
            trxTerimaLocalRepository.update(stockModel.getNoDo(), stockModel.getKodeArt(), stockModel.getUkuran(), stockModel.getQtyTerima() + 1);
            return trxTerimaLocalRepository.getTrxTerimaStock(noDo);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(data -> {
                            ProductReceiveStockViewState.UPDATED_STATE.setData(data);
                            postValue(ProductReceiveStockViewState.UPDATED_STATE);
                        },
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            ProductReceiveStockViewState.UPDATE_FAILED_STATE.setError(throwable);
                            postValue(ProductReceiveStockViewState.UPDATE_FAILED_STATE);
                        }));
    }
    public void updateDateAndNotes(String noDo, Date date, String note) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ProductReceiveStockViewState.LOADING_STATE);
            trxTerimaLocalRepository.updateDateAndNote(noDo, date, note);
            receiveSummaryUiModelMutableLiveData.postValue(
                    new ProductReceiveSummaryUiModel(date, note));
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(data -> postValue(ProductReceiveStockViewState.FINISH_STATE),
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            ProductReceiveStockViewState.ERROR_STATE.setError(throwable);
                            postValue(ProductReceiveStockViewState.ERROR_STATE);
                        }));
    }

}
