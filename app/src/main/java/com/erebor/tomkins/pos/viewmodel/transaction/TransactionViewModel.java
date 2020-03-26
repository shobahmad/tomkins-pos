package com.erebor.tomkins.pos.viewmodel.transaction;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.dao.MsBarcodeDao;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBarcodeDBModel;
import com.erebor.tomkins.pos.data.ui.TransactionDetailUiModel;
import com.erebor.tomkins.pos.data.ui.TransactionUiModel;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class TransactionViewModel extends BaseViewModel<TransactionViewState> {

    private MsBarcodeDao msBarcodeDao;
    private MsArtDao msArtDao;
    private SharedPrefs sharedPrefs;
    private ResourceHelper resourceHelper;
    private Logger logger;


    @Inject
    public TransactionViewModel(Logger logger, MsBarcodeDao msBarcodeDao, MsArtDao msArtDao, SharedPrefs sharedPrefs, ResourceHelper resourceHelper) {
        this.logger = logger;
        this.msBarcodeDao = msBarcodeDao;
        this.msArtDao = msArtDao;
        this.sharedPrefs = sharedPrefs;
        this.resourceHelper = resourceHelper;
    }

    public void scanBarcode(String barcode) {
        getDisposable().add(Single.fromCallable(() -> barcodeValidation(barcode))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> postValue(state),
                        throwable -> {
                            TransactionViewState.ERROR_STATE.setError(throwable);
                            postValue(TransactionViewState.ERROR_STATE);
                        }));
    }

    public void updateQuantity(String barcode, int qty) {
        getDisposable().add(Single.fromCallable(() -> {
            logger.debug(getClass().getSimpleName(), "Update qty of " + barcode + " to " + qty);
            double existingPrice = 0;
            double newPrice = 0;
            TransactionUiModel transactionUiModel = TransactionViewState.FOUND_STATE.getData();
            ArrayList<TransactionDetailUiModel> list = transactionUiModel.getListTransaction();
            for (int i = 0; i < list.size(); i++) {
                TransactionDetailUiModel detail = list.get(i);
                if (!detail.getBarcode().equals(barcode)) {
                    continue;
                }

                existingPrice = detail.getHargaJual() * detail.getQty();
                newPrice = detail.getHargaJual() * qty;

                logger.debug(getClass().getSimpleName(), "Update grand total > " + existingPrice + " to " + newPrice);
                TransactionDetailUiModel updatedDetail = new TransactionDetailUiModel(
                        detail.getIndTrx(),
                        detail.getArtName(),
                        detail.getArtCode(),
                        detail.getBarcode(),
                        detail.getSize(),
                        detail.getHargaNormal(),
                        detail.getEventName(),
                        qty,
                        detail.getHargaJual(),
                        detail.getNote()
                );
                list.set(i, updatedDetail);
                break;
            }

            TransactionUiModel updatedTransaction = new TransactionUiModel(
                    transactionUiModel.getTransactionId(),
                    transactionUiModel.getTransactionDate(),
                    transactionUiModel.getGrandTotal() - existingPrice + newPrice,
                    list
            );
            TransactionViewState.FOUND_STATE.setData(updatedTransaction);
            return TransactionViewState.FOUND_STATE;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> postValue(state),
                        throwable -> {
                            TransactionViewState.ERROR_STATE.setError(throwable);
                            postValue(TransactionViewState.ERROR_STATE);
                        }));
    }
    public void updateNote(String barcode, String note) {
        getDisposable().add(Single.fromCallable(() -> {
            logger.debug(getClass().getSimpleName(), "Update note of " + barcode + " to " + note);
            TransactionUiModel transactionUiModel = TransactionViewState.FOUND_STATE.getData();
            ArrayList<TransactionDetailUiModel> list = transactionUiModel.getListTransaction();
            for (int i = 0; i < list.size(); i++) {
                TransactionDetailUiModel detail = list.get(i);
                if (!detail.getBarcode().equals(barcode)) {
                    continue;
                }

                TransactionDetailUiModel updatedDetail = new TransactionDetailUiModel(
                        detail.getIndTrx(),
                        detail.getArtName(),
                        detail.getArtCode(),
                        detail.getBarcode(),
                        detail.getSize(),
                        detail.getHargaNormal(),
                        detail.getEventName(),
                        detail.getQty(),
                        detail.getHargaJual(),
                        note
                );
                list.set(i, updatedDetail);
                break;
            }

            TransactionViewState.FOUND_STATE.setData(transactionUiModel);
            return TransactionViewState.FOUND_STATE;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> postValue(state),
                        throwable -> {
                            TransactionViewState.ERROR_STATE.setError(throwable);
                            postValue(TransactionViewState.ERROR_STATE);
                        }));
    }

    private TransactionViewState barcodeValidation(String barcode) {
        //@ get barcode
        MsBarcodeDBModel msBarcodeDBModel = msBarcodeDao.getSyncByNoBarcode(barcode);
        if (msBarcodeDBModel == null) {
            return TransactionViewState.NOT_FOUND_STATE;
        }

        //@ get article
        MsArtDBModel msArtDBModel = msArtDao.getSyncByKodeArt(msBarcodeDBModel.getKodeArt());
        if (msArtDBModel == null) {
            TransactionViewState.ERROR_STATE.setError(new Exception(resourceHelper.getResourceString(R.string.art_not_found)));
            return TransactionViewState.ERROR_STATE;
        }
        //@ create detail
        String transidGenerated = sharedPrefs.getKodeSPG() + System.currentTimeMillis();
        TransactionDetailUiModel newestDetail = new TransactionDetailUiModel(
                transidGenerated,
                msArtDBModel.getNamaArt(),
                msArtDBModel.getKodeArt(),
                msBarcodeDBModel.getNoBarcode(),
                msBarcodeDBModel.getUkuran(),
                msArtDBModel.getHarga(),
                "",
                1,
                msArtDBModel.getHarga(),
                ""
        );

        TransactionUiModel transactionUiModel = TransactionViewState.FOUND_STATE.getData();
        if (transactionUiModel == null) {
            transactionUiModel = new TransactionUiModel("", Calendar.getInstance().getTime(), newestDetail.getHargaJual(), new ArrayList<TransactionDetailUiModel>() {
                {
                    add(newestDetail);
                }
            });


            TransactionViewState.FOUND_STATE.setData(transactionUiModel);
            return TransactionViewState.FOUND_STATE;
        }

        //@ check update detail
        ArrayList<TransactionDetailUiModel> list = transactionUiModel.getListTransaction();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            TransactionDetailUiModel detail = list.get(i);
            if (!detail.getBarcode().equals(newestDetail.getBarcode())) {
                continue;
            }

            found = true;
            TransactionDetailUiModel updatedDetail = new TransactionDetailUiModel(
                    detail.getIndTrx(),
                    detail.getArtName(),
                    detail.getArtCode(),
                    detail.getBarcode(),
                    detail.getSize(),
                    detail.getHargaNormal(),
                    detail.getEventName(),
                    detail.getQty() + newestDetail.getQty(),
                    detail.getHargaJual(),
                    detail.getNote()
            );
            list.set(i, updatedDetail);
            break;
        }

        if (!found) {
            list.add(newestDetail);
        }


        TransactionUiModel updatedTransaction = new TransactionUiModel(
                transactionUiModel.getTransactionId(),
                transactionUiModel.getTransactionDate(),
                transactionUiModel.getGrandTotal() + newestDetail.getHargaJual(),
                list
        );

        TransactionViewState.FOUND_STATE.setData(updatedTransaction);
        return TransactionViewState.FOUND_STATE;
    }
}
