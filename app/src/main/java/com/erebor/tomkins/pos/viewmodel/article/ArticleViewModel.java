package com.erebor.tomkins.pos.viewmodel.article;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.ArtGradeDao;
import com.erebor.tomkins.pos.data.local.dao.EventDiscountDao;
import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.dao.MsBarcodeDao;
import com.erebor.tomkins.pos.data.local.model.ArtGradeDBModel;
import com.erebor.tomkins.pos.data.local.model.EventDiscountModel;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBarcodeDBModel;
import com.erebor.tomkins.pos.data.ui.ArticleUiModel;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewState;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ArticleViewModel extends BaseViewModel<ArticleViewState> {
    private final MsBarcodeDao msBarcodeDao;
    private final MsArtDao msArtDao;
    private final ResourceHelper resourceHelper;
    private final EventDiscountDao eventDiscountDao;
    private final ArtGradeDao artGradeDao;

    @Inject
    public ArticleViewModel(MsBarcodeDao msBarcodeDao, MsArtDao msArtDao, ResourceHelper resourceHelper,
                            EventDiscountDao eventDiscountDao, ArtGradeDao artGradeDao) {
        this.msBarcodeDao = msBarcodeDao;
        this.msArtDao = msArtDao;
        this.resourceHelper = resourceHelper;
        this.eventDiscountDao = eventDiscountDao;
        this.artGradeDao = artGradeDao;
    }

    public void searchArticle(String keyword) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ArticleViewState.LOADING_STATE);

            boolean isBarcode = false;
            try {
                double d = Double.parseDouble(keyword.trim());
                isBarcode = true;
            } catch (NumberFormatException nfe) {
                isBarcode = false;
            }


            List<ArticleUiModel> searchResult = new ArrayList<>();
            if (isBarcode) {
                //@ get barcode
                MsBarcodeDBModel msBarcodeDBModel = msBarcodeDao.getByNoBarcode(keyword.trim());
                if (msBarcodeDBModel == null) {
                    return ArticleViewState.NOT_FOUND_STATE;
                }

                ArticleUiModel articleUiModel = getArticle(msBarcodeDBModel.getKodeArt(),
                                                            msBarcodeDBModel.getNoBarcode(),
                                                            msBarcodeDBModel.getUkuran(),
                                                    true);
                if (articleUiModel == null) {
                    ArticleViewState.NOT_FOUND_STATE.setError(new Exception(resourceHelper.getResourceString(R.string.art_not_found)));
                    return ArticleViewState.NOT_FOUND_STATE;
                }
                searchResult.add(articleUiModel);
                ArticleViewState.FOUND_STATE.setData(searchResult);
                return ArticleViewState.FOUND_STATE;
            }

            List<MsBarcodeDBModel> msBarcodeDBModels = msBarcodeDao.getListByKodeArt(keyword.trim().toUpperCase());
            if (msBarcodeDBModels == null || msBarcodeDBModels.isEmpty()) {
                ArticleViewState.NOT_FOUND_STATE.setError(new Exception(resourceHelper.getResourceString(R.string.art_not_found)));
                return ArticleViewState.NOT_FOUND_STATE;
            }

            for (MsBarcodeDBModel msBarcodeDBModel : msBarcodeDBModels) {
                ArticleUiModel articleUiModel = getArticle(msBarcodeDBModel.getKodeArt(),
                        msBarcodeDBModel.getNoBarcode(),
                        msBarcodeDBModel.getUkuran(),
                        msBarcodeDBModels.size() == 1);
                if (articleUiModel == null) {
                    continue;
                }
                searchResult.add(articleUiModel);
            }

            ArticleViewState.FOUND_STATE.setData(searchResult);
            return ArticleViewState.FOUND_STATE;
            
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> {
                            postValue(state);
                        },
                        throwable -> {
                            ArticleViewState.ERROR_STATE.setError(throwable);
                            postValue(ArticleViewState.ERROR_STATE);
                        }));
    }

    public void selectArticle(String barcode) {
        getDisposable().add(Single.fromCallable(() -> {
            List<ArticleUiModel> searchResult = ArticleViewState.FOUND_STATE.getData();
            postValue(ArticleViewState.LOADING_STATE);
            List<ArticleUiModel> selectResult = new ArrayList<>();
            for (ArticleUiModel articleUiModel : searchResult) {
                selectResult.add(new ArticleUiModel(articleUiModel, articleUiModel.getBarcode().equals(barcode)));
            }

            ArticleViewState.FOUND_STATE.setData(selectResult);
            return ArticleViewState.FOUND_STATE;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> {
                            postValue(state);
                        },
                        throwable -> {
                            ArticleViewState.ERROR_STATE.setError(throwable);
                            postValue(ArticleViewState.ERROR_STATE);
                        }));
    }

    public void updateGrade(String barcode, String grade) {
        getDisposable().add(Single.fromCallable(() -> {
            postValue(ArticleViewState.LOADING_STATE);
            ArtGradeDBModel artGradeDBModel = new ArtGradeDBModel(barcode, grade);
            artGradeDao.insertReplaceSync(artGradeDBModel);

            List<ArticleUiModel> searchResult = ArticleViewState.FOUND_STATE.getData();

            for (int i = 0; i < searchResult.size(); i++) {
                ArticleUiModel articleUiModel = searchResult.get(i);
                if (!articleUiModel.getBarcode().equals(barcode)) {
                    continue;
                }
                searchResult.set(i, new ArticleUiModel(articleUiModel, grade));
                break;
            }

            ArticleViewState.FOUND_STATE.setData(searchResult);
            return ArticleViewState.FOUND_STATE;


        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(state -> {
                            postValue(state);
                        },
                        throwable -> {
                            ArticleViewState.ERROR_STATE.setError(throwable);
                            postValue(ArticleViewState.ERROR_STATE);
                        }));
    }

    private ArticleUiModel getArticle(String kodeArt, String barcode, String size, boolean selected) {
        MsArtDBModel msArtDBModel = msArtDao.getByKodeArt(kodeArt);
        if (msArtDBModel == null) {
            return null;
        }

        //@ get discount
        List<EventDiscountModel> eventDiscountModels = eventDiscountDao.getPrice(kodeArt);
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

        ArtGradeDBModel artGradeDBModel = artGradeDao.getByBarcode(barcode);

        //@ create detail
        return new ArticleUiModel(
                msArtDBModel.getNamaArt(),
                msArtDBModel.getKodeArt(),
                barcode,
                size,
                msArtDBModel.getWarna(),
                msArtDBModel.getHarga(),
                kodeEvent,
                1,
                diskon,
                hargaKhusus,
                hargaJual,
                selected,
                artGradeDBModel == null ? "A" : artGradeDBModel.getGrade()
        );
    }
}
