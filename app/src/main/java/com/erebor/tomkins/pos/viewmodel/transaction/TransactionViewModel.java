package com.erebor.tomkins.pos.viewmodel.transaction;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.EventDiscountDao;
import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.dao.MsBarcodeDao;
import com.erebor.tomkins.pos.data.local.dao.TrxJualDao;
import com.erebor.tomkins.pos.data.local.dao.TrxJualDetDao;
import com.erebor.tomkins.pos.data.local.model.EventDiscountModel;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBarcodeDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxJualDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxJualDetDBModel;
import com.erebor.tomkins.pos.data.ui.TransactionDetailUiModel;
import com.erebor.tomkins.pos.data.ui.TransactionUiModel;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class TransactionViewModel extends BaseViewModel<TransactionViewState> {

    private MsBarcodeDao msBarcodeDao;
    private MsArtDao msArtDao;
    private TrxJualDao trxJualDao;
    private TrxJualDetDao trxJualDetDao;
    private SharedPrefs sharedPrefs;
    private EventDiscountDao eventDiscountDao;
    private ResourceHelper resourceHelper;
    private Logger logger;
    private DateConverterHelper dateConverterHelper;

    @Inject
    public TransactionViewModel(MsBarcodeDao msBarcodeDao, MsArtDao msArtDao, TrxJualDao trxJualDao, TrxJualDetDao trxJualDetDao, SharedPrefs sharedPrefs, EventDiscountDao eventDiscountDao, ResourceHelper resourceHelper, Logger logger, DateConverterHelper dateConverterHelper) {
        this.msBarcodeDao = msBarcodeDao;
        this.msArtDao = msArtDao;
        this.trxJualDao = trxJualDao;
        this.trxJualDetDao = trxJualDetDao;
        this.sharedPrefs = sharedPrefs;
        this.eventDiscountDao = eventDiscountDao;
        this.resourceHelper = resourceHelper;
        this.logger = logger;
        this.dateConverterHelper = dateConverterHelper;
    }

    public void scanBarcode(String barcode, boolean isSale) {
        getDisposable().add(Single.fromCallable(() -> barcodeValidation(barcode, isSale))
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
                        detail.getColour(),
                        detail.getHargaNormal(),
                        detail.getEventCode(),
                        qty,
                        detail.getDiskon(),
                        detail.getHargaKhusus(),
                        detail.getHargaJual(),
                        detail.getNote()
                );
                list.set(i, updatedDetail);
                break;
            }

            TransactionUiModel updatedTransaction = new TransactionUiModel(
                    transactionUiModel.getBarcode(),
                    transactionUiModel.getTransactionId(),
                    transactionUiModel.getTransactionDate(),
                    transactionUiModel.getGrandTotal() - existingPrice + newPrice,
                    transactionUiModel.isSale(),
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
                        detail.getColour(),
                        detail.getHargaNormal(),
                        detail.getEventCode(),
                        detail.getQty(),
                        detail.getDiskon(),
                        detail.getHargaKhusus(),
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
    public void updatDiscount(String barcode, double discount) {
        getDisposable().add(Single.fromCallable(() -> {
            logger.debug(getClass().getSimpleName(), "Update discount of " + barcode + " to " + discount);
            TransactionUiModel transactionUiModel = TransactionViewState.FOUND_STATE.getData();
            TransactionUiModel updatedTransaction = null;
            ArrayList<TransactionDetailUiModel> list = transactionUiModel.getListTransaction();
            for (int i = 0; i < list.size(); i++) {
                TransactionDetailUiModel detail = list.get(i);
                if (!detail.getBarcode().equals(barcode)) {
                    continue;
                }

                double beforeChanged = detail.getHargaJual() * detail.getQty();
                double beforeDiscount = detail.getHargaKhusus() != 0 ? detail.getHargaKhusus() : detail.getHargaJual();
                TransactionDetailUiModel updatedDetail = new TransactionDetailUiModel(
                        detail.getIndTrx(),
                        detail.getArtName(),
                        detail.getArtCode(),
                        detail.getBarcode(),
                        detail.getSize(),
                        detail.getColour(),
                        detail.getHargaNormal(),
                        detail.getEventCode(),
                        detail.getQty(),
                        discount,
                        detail.getHargaKhusus(),
                        beforeDiscount - (beforeDiscount * discount/100),
                        detail.getNote()
                );
                list.set(i, updatedDetail);

                updatedTransaction = new TransactionUiModel(
                        transactionUiModel.getBarcode(),
                        transactionUiModel.getTransactionId(),
                        transactionUiModel.getTransactionDate(),
                        transactionUiModel.getGrandTotal() - beforeChanged + (updatedDetail.getHargaJual() * updatedDetail.getQty()),
                        transactionUiModel.isSale(),
                        list);
                break;
            }

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
    public void updatSellingPrice(String barcode, double price) {
        getDisposable().add(Single.fromCallable(() -> {
            logger.debug(getClass().getSimpleName(), "Update selling price of " + barcode + " to " + price);
            TransactionUiModel transactionUiModel = TransactionViewState.FOUND_STATE.getData();
            TransactionUiModel updatedTransaction = null;
            ArrayList<TransactionDetailUiModel> list = transactionUiModel.getListTransaction();
            for (int i = 0; i < list.size(); i++) {
                TransactionDetailUiModel detail = list.get(i);
                if (!detail.getBarcode().equals(barcode)) {
                    continue;
                }

                double beforeChanged = detail.getHargaJual() * detail.getQty();
                TransactionDetailUiModel updatedDetail = new TransactionDetailUiModel(
                        detail.getIndTrx(),
                        detail.getArtName(),
                        detail.getArtCode(),
                        detail.getBarcode(),
                        detail.getSize(),
                        detail.getColour(),
                        detail.getHargaNormal(),
                        detail.getEventCode(),
                        detail.getQty(),
                        0,
                        detail.getHargaKhusus(),
                        price,
                        detail.getNote()
                );
                list.set(i, updatedDetail);

                updatedTransaction = new TransactionUiModel(
                        transactionUiModel.getBarcode(),
                        transactionUiModel.getTransactionId(),
                        transactionUiModel.getTransactionDate(),
                        transactionUiModel.getGrandTotal() - beforeChanged + (updatedDetail.getHargaJual() * updatedDetail.getQty()),
                        transactionUiModel.isSale(),
                        list);
                break;
            }

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

    public void reset() {
        getDisposable().add(Single.fromCallable(() -> {
            TransactionViewState.FOUND_STATE.setData(null);
            TransactionViewState.SUCCESS_STATE.setData(null);
            TransactionViewState.SAVING_STATE.setData(null);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> postValue(TransactionViewState.RESET_STATE)));
    }

    private TransactionViewState barcodeValidation(String barcode, boolean isSale) {
        //@ get barcode
        MsBarcodeDBModel msBarcodeDBModel = msBarcodeDao.getByNoBarcode(barcode);
        if (msBarcodeDBModel == null) {
            TransactionViewState.NOT_FOUND_STATE.setData(new TransactionUiModel(barcode, "", Calendar.getInstance().getTime(), 0, isSale, new ArrayList<>()));
            return TransactionViewState.NOT_FOUND_STATE;
        }

        //@ get article
        MsArtDBModel msArtDBModel = msArtDao.getByKodeArt(msBarcodeDBModel.getKodeArt());
        if (msArtDBModel == null) {
            TransactionViewState.ERROR_STATE.setError(new Exception(resourceHelper.getResourceString(R.string.art_not_found)));
            return TransactionViewState.ERROR_STATE;
        }

        //@ get discount
        List<EventDiscountModel> eventDiscountModels = eventDiscountDao.getPrice(msBarcodeDBModel.getKodeArt());
        double hargaJual = msArtDBModel.getHarga();
        double diskon = 0;
        double hargaKhusus = 0;
        String kodeEvent = "";
        HARGA_JUAL: {
            if (eventDiscountModels == null || eventDiscountModels.isEmpty()) {
                hargaJual = msArtDBModel.getHarga();
                break HARGA_JUAL;
            }

            Date curdate = Calendar.getInstance().getTime();
            for (EventDiscountModel eventDiscountModel : eventDiscountModels) {
                if (curdate.before(eventDiscountModel.tglDari)) {
                    continue;
                }

                if (curdate.after(eventDiscountModel.tglSampai)) {
                    continue;
                }

                if (eventDiscountModel.hargaKhusus != 0) {
                    diskon = eventDiscountModel.diskon;
                    hargaJual = eventDiscountModel.hargaKhusus - (eventDiscountModel.hargaKhusus * eventDiscountModel.diskon / 100);
                    hargaKhusus = eventDiscountModel.hargaKhusus;
                    kodeEvent = eventDiscountModel.kodeEvent;
                    break HARGA_JUAL;
                }

                diskon = eventDiscountModel.diskon;
                hargaJual = msArtDBModel.getHarga() - (msArtDBModel.getHarga() * eventDiscountModel.diskon / 100);
                hargaKhusus = eventDiscountModel.hargaKhusus;
                kodeEvent = eventDiscountModel.kodeEvent;
            }
        }

        //@ create detail
        String transidGenerated = generateIndTrx();
        TransactionDetailUiModel newestDetail = new TransactionDetailUiModel(
                transidGenerated,
                msArtDBModel.getNamaArt(),
                msArtDBModel.getKodeArt(),
                msBarcodeDBModel.getNoBarcode(),
                msBarcodeDBModel.getUkuran(),
                msArtDBModel.getWarna(),
                msArtDBModel.getHarga(),
                kodeEvent,
                1,
                diskon,
                hargaKhusus,
                hargaJual,
                ""
        );

        TransactionUiModel transactionUiModel = TransactionViewState.FOUND_STATE.getData();
        if (transactionUiModel == null) {
            transactionUiModel = new TransactionUiModel(barcode, "", Calendar.getInstance().getTime(), newestDetail.getHargaJual(), isSale, new ArrayList<TransactionDetailUiModel>() {
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
                    detail.getColour(),
                    detail.getHargaNormal(),
                    detail.getEventCode(),
                    detail.getQty() + newestDetail.getQty(),
                    detail.getDiskon(),
                    detail.getHargaJual(),
                    detail.getHargaKhusus(),
                    detail.getNote()
            );
            list.set(i, updatedDetail);
            break;
        }

        if (!found) {
            list.add(newestDetail);
        }


        TransactionUiModel updatedTransaction = new TransactionUiModel(
                transactionUiModel.getBarcode(),
                transactionUiModel.getTransactionId(),
                transactionUiModel.getTransactionDate(),
                transactionUiModel.getGrandTotal() + newestDetail.getHargaJual(),
                isSale, list
        );

        TransactionViewState.FOUND_STATE.setData(updatedTransaction);
        return TransactionViewState.FOUND_STATE;
    }

    public void saveTransaction(Date transactionDate) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(TransactionViewState.SAVING_STATE);
            try {
                TransactionUiModel transactionUiModel = TransactionViewState.FOUND_STATE.getData();

                TrxJualDBModel trxJualDBModel = getTrxJual(transactionDate);
                ArrayList<TransactionDetailUiModel> list = transactionUiModel.getListTransaction();
                trxJualDBModel.setListDetail(new ArrayList<>());
                for (int i = 0; i < list.size(); i++) {
                    TransactionDetailUiModel detail = list.get(i);
                    trxJualDBModel.getListDetail().add(getTrxJualDet(detail, trxJualDBModel.getNoBon(), transactionUiModel.isSale()));
                }

                trxJualDao.insertReplaceSync(trxJualDBModel);
                trxJualDetDao.insertAllReplaceSync(trxJualDBModel.getListDetail());

                transactionUiModel.getListTransaction().clear();
                TransactionUiModel successTransaction = new TransactionUiModel(
                        transactionUiModel.getBarcode(),
                        trxJualDBModel.getNoBon(),
                        transactionUiModel.getTransactionDate(),
                        transactionUiModel.getGrandTotal(),
                        transactionUiModel.isSale(),
                        transactionUiModel.getListTransaction()
                );
                TransactionViewState.SUCCESS_STATE.setData(successTransaction);
                return TransactionViewState.SUCCESS_STATE;
            } catch (Exception e) {
                logger.error(getClass().getSimpleName(), e.getMessage(), e);
                TransactionViewState.FAILED_STATE.setError(e);
                return TransactionViewState.FAILED_STATE;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> postValue(state),
                        throwable -> {
                            TransactionViewState.FAILED_STATE.setError(throwable);
                            postValue(TransactionViewState.ERROR_STATE);
                        }));

    }

    private TrxJualDBModel getTrxJual(Date transactionDate) {
        TrxJualDBModel trxJualDBModel = new TrxJualDBModel();
        trxJualDBModel.setNoBon(generateNoBon(transactionDate));
        trxJualDBModel.setTanggal(transactionDate);
        trxJualDBModel.setKodeSPG(sharedPrefs.getKodeSPG());
        trxJualDBModel.setKodeCounter(sharedPrefs.getKodeKonter());
        return trxJualDBModel;
    }

    private TrxJualDetDBModel getTrxJualDet(TransactionDetailUiModel transactionDetailUiModel, String NoBon, boolean sale) {
        TrxJualDetDBModel trxJualDetDBModel = new TrxJualDetDBModel();
        trxJualDetDBModel.setNoBon(NoBon);
        trxJualDetDBModel.setIndTrx(transactionDetailUiModel.getIndTrx());
        trxJualDetDBModel.setKodeArt(transactionDetailUiModel.getArtCode());
        trxJualDetDBModel.setUkuran(transactionDetailUiModel.getSize());
        trxJualDetDBModel.setHargaNormal(transactionDetailUiModel.getHargaNormal());
        trxJualDetDBModel.setKodeEvent(transactionDetailUiModel.getEventCode());
        trxJualDetDBModel.setQtyJual(transactionDetailUiModel.getQty() * (sale ? 1 : -1));
        trxJualDetDBModel.setDiskon(transactionDetailUiModel.getDiskon());
        trxJualDetDBModel.setHrgaJual(transactionDetailUiModel.getHargaJual());
        trxJualDetDBModel.setCatatan((sale ? "" : "RETURN") + (transactionDetailUiModel.getNote().isEmpty() ? "" : " | ") + transactionDetailUiModel.getNote());
        return trxJualDetDBModel;
    }

    private String generateNoBon(Date transactionDate) {
        return dateConverterHelper.toDateNoBon(transactionDate) + "_" + sharedPrefs.getKodeSPG() + "_" + Long.toHexString(System.currentTimeMillis()/1000).toUpperCase();
    }
    private String generateIndTrx() {
        TransactionUiModel transactionUiModel = TransactionViewState.FOUND_STATE.getData();
        int sequence = transactionUiModel == null ? 1 : transactionUiModel.getListTransaction().size() + 1;
        return sharedPrefs.getKodeSPG() + Long.toHexString(System.currentTimeMillis()/1000).toUpperCase() + sequence;
    }

}
