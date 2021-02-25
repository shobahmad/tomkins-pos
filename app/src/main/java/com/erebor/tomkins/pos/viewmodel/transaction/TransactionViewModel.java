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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


    public void loadTransaction(Date transactionDateParam) {
        getDisposable().add(Single.fromCallable(() -> {
            Date transactionDate = transactionDateParam == null ? Calendar.getInstance().getTime() : transactionDateParam;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            List<TrxJualDBModel> trxJualDBModels = trxJualDao.getByDate(simpleDateFormat.format(transactionDate));
            for (TrxJualDBModel trxJualDBModel : trxJualDBModels) {
                trxJualDBModel.setListDetail(trxJualDetDao.getListByNoBon(trxJualDBModel.getNoBon()));
            }

            ArrayList<TransactionDetailUiModel> transactionDetailUiModels = new ArrayList<>();
            for (TrxJualDBModel trxJualDBModel : trxJualDBModels) {
                for (TrxJualDetDBModel trxJualDetDBModel : trxJualDBModel.getListDetail()) {
                    MsArtDBModel msArtDBModel = msArtDao.getByKodeArt(trxJualDetDBModel.getKodeArt());
                    MsBarcodeDBModel msBarcodeDBModel = msBarcodeDao.getByNoBarcode(trxJualDetDBModel.getNoBarcode());

                    TransactionDetailUiModel newestDetail = new TransactionDetailUiModel(
                            trxJualDBModel.getNoBon(),
                            dateConverterHelper.toTimeString(trxJualDBModel.getTanggal()),
                            msArtDBModel.getNamaArt(),
                            msArtDBModel.getKodeArt(),
                            msBarcodeDBModel.getNoBarcode(),
                            msBarcodeDBModel.getUkuran(),
                            msArtDBModel.getWarna(),
                            msArtDBModel.getHarga(),
                            trxJualDetDBModel.getKodeEvent(),
                            trxJualDetDBModel.getQtyJual(),
                            trxJualDetDBModel.getDiskon(),
                            trxJualDetDBModel.getHargaNormal(),
                            trxJualDetDBModel.getHrgaJual(),
                            trxJualDetDBModel.getCatatan(),
                            trxJualDBModel.isUploaded());
                    transactionDetailUiModels.add(0, newestDetail);
                }
            }

            TransactionUiModel transactionUiModel = new TransactionUiModel(
                    "",
                    "",
                    transactionDate,
                    0,
                    true,
                    transactionDetailUiModels
            );



            TransactionViewState.LOADED_STATE.setData(transactionUiModel);
            return TransactionViewState.LOADED_STATE;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> postValue(state),
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
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

    public void saveTransaction(Date transactionDate, String barcode, boolean isSale, String note, String discount, String price) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(TransactionViewState.SAVING_STATE);
            try {
                TrxJualDBModel trxJualDBModel = getTrxJual(transactionDate);
                trxJualDBModel.setListDetail(new ArrayList<>());
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
                double hargaKhusus = 0;
                String kodeEvent = "";
                HARGA_JUAL: {
                    if (eventDiscountModels == null || eventDiscountModels.isEmpty()) {
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
                            hargaKhusus = eventDiscountModel.hargaKhusus;
                            kodeEvent = eventDiscountModel.kodeEvent;
                            break HARGA_JUAL;
                        }

                        hargaKhusus = eventDiscountModel.hargaKhusus;
                        kodeEvent = eventDiscountModel.kodeEvent;
                    }
                }

                //@ create detail
                String transidGenerated = generateIndTrx();
                TransactionDetailUiModel newestDetail = new TransactionDetailUiModel(
                        transidGenerated,
                        dateConverterHelper.toTimeString(transactionDate), msArtDBModel.getNamaArt(),
                        msArtDBModel.getKodeArt(),
                        msBarcodeDBModel.getNoBarcode(),
                        msBarcodeDBModel.getUkuran(),
                        msArtDBModel.getWarna(),
                        msArtDBModel.getHarga(),
                        kodeEvent,
                        isSale ? 1 : -1,
                        Double.parseDouble(discount),
                        hargaKhusus,
                        Double.parseDouble(price),
                        note,
                        false);

                trxJualDBModel.getListDetail().add(getTrxJualDet(newestDetail, trxJualDBModel.getNoBon(), isSale));

                trxJualDao.insertReplaceSync(trxJualDBModel);
                trxJualDetDao.insertAllReplaceSync(trxJualDBModel.getListDetail());

                TransactionUiModel successTransaction = new TransactionUiModel(
                        barcode,
                        trxJualDBModel.getNoBon(),
                        transactionDate,
                        0,
                        isSale,
                        new ArrayList<TransactionDetailUiModel>() {
                            {
                                add(newestDetail);
                            }
                        }
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
        trxJualDetDBModel.setNoBarcode(transactionDetailUiModel.getBarcode());
        trxJualDetDBModel.setKodeArt(transactionDetailUiModel.getArtCode());
        trxJualDetDBModel.setUkuran(transactionDetailUiModel.getSize());
        trxJualDetDBModel.setHargaNormal(transactionDetailUiModel.getHargaNormal());
        trxJualDetDBModel.setKodeEvent(transactionDetailUiModel.getEventCode());
        trxJualDetDBModel.setQtyJual(transactionDetailUiModel.getQty());
        trxJualDetDBModel.setDiskon(transactionDetailUiModel.getDiskon());
        trxJualDetDBModel.setHrgaJual(transactionDetailUiModel.getHargaJual());
        trxJualDetDBModel.setCatatan(transactionDetailUiModel.getNote());
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
