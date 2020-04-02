package com.erebor.tomkins.pos.viewmodel.login;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.EventHargaDao;
import com.erebor.tomkins.pos.data.local.dao.EventHargaDetDao;
import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.dao.MsBarcodeDao;
import com.erebor.tomkins.pos.data.local.dao.MsBrandDao;
import com.erebor.tomkins.pos.data.local.dao.MsUkuranDao;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.dao.TrxJualDao;
import com.erebor.tomkins.pos.data.local.dao.TrxJualDetDao;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.remote.LoginRequest;
import com.erebor.tomkins.pos.data.ui.UserUiModel;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginViewState> {

    private final Logger logger;
    private final SharedPrefs sharedPrefs;
    private final TomkinsService service;
    private final ResourceHelper resourceHelper;

    private final TrxJualDao trxJualDao;
    private final TrxJualDetDao trxJualDetDao;
    private final EventHargaDao eventHargaDao;
    private final EventHargaDetDao eventHargaDetDao;
    private final MsArtDao msArtDao;
    private final MsBarcodeDao msBarcodeDao;
    private final MsBrandDao msBrandDao;
    private final MsUkuranDao msUkuranDao;
    private final StokRealDao stokRealDao;

    @Inject
    public LoginViewModel(Logger logger, SharedPrefs sharedPrefs, TomkinsService service, ResourceHelper resourceHelper, TrxJualDao trxJualDao, TrxJualDetDao trxJualDetDao, EventHargaDao eventHargaDao, EventHargaDetDao eventHargaDetDao, MsArtDao msArtDao, MsBarcodeDao msBarcodeDao, MsBrandDao msBrandDao, MsUkuranDao msUkuranDao, StokRealDao stokRealDao) {
        this.logger = logger;
        this.sharedPrefs = sharedPrefs;
        this.service = service;
        this.resourceHelper = resourceHelper;
        this.trxJualDao = trxJualDao;
        this.trxJualDetDao = trxJualDetDao;
        this.eventHargaDao = eventHargaDao;
        this.eventHargaDetDao = eventHargaDetDao;
        this.msArtDao = msArtDao;
        this.msBarcodeDao = msBarcodeDao;
        this.msBrandDao = msBrandDao;
        this.msUkuranDao = msUkuranDao;
        this.stokRealDao = stokRealDao;
    }

    public void postLogin(String username, String password) {
        setValue(LoginViewState.LOADING_STATE);

        getDisposable().add(service.postLogin(new LoginRequest(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(loginResponse -> {
                            if (loginResponse.statusIsError()) {
                                LoginViewState.ERROR_STATE.setError(new Exception(loginResponse.getMessage()));
                                postValue(LoginViewState.ERROR_STATE);
                                return;
                            }
                            sharedPrefs.setUsername(username);
                            sharedPrefs.setPassword(password);
                            sharedPrefs.setKodeKonter(loginResponse.getResult().getKodeKonter());
                            sharedPrefs.setNamaKonter(loginResponse.getResult().getNamaKonter());
                            sharedPrefs.setKodeSPG(loginResponse.getResult().getKodeSPG());
                            sharedPrefs.setNamaSPG(loginResponse.getResult().getNamaSPG());
                            sharedPrefs.setToken(loginResponse.getResult().getToken());

                            UserUiModel userUiModel = new UserUiModel(
                                    loginResponse.getResult().getKodeSPG(),
                                    loginResponse.getResult().getNamaSPG(),
                                    loginResponse.getResult().getKodeKonter(),
                                    loginResponse.getResult().getNamaKonter()
                            );

                            LoginViewState.LOGIN_VALID_STATE.setData(userUiModel);
                            postValue(LoginViewState.LOGIN_VALID_STATE);
                        },
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            LoginViewState.ERROR_STATE.setError(throwable);
                            postValue(LoginViewState.ERROR_STATE);
                        })
        );
    }
    public void logout() {
        setValue(LoginViewState.LOADING_STATE);

        getDisposable().add(Single.fromCallable(() -> {
                    clearSession();
                    clearData();

                    MsArtDBModel msArtDBModel = msArtDao.getSyncLatest();
                    logger.debug(getClass().getSimpleName(), "msArtDBModel: " + msArtDBModel);

                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(loginResponse -> {
                            postValue(LoginViewState.LOGOUT_VALID_STATE);
                        },
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            LoginViewState.ERROR_STATE.setError(throwable);
                            postValue(LoginViewState.ERROR_STATE);
                        })
        );
    }

    private void clearData() {
        trxJualDao.truncate();
        trxJualDetDao.truncate();
        eventHargaDao.truncate();
        eventHargaDetDao.truncate();
        msArtDao.truncate();
        msBarcodeDao.truncate();
        msBrandDao.truncate();
        msUkuranDao.truncate();
        stokRealDao.truncate();

    }
    private void clearSession() {
        sharedPrefs.setUsername("");
        sharedPrefs.setPassword("");
        sharedPrefs.setKodeKonter("");
        sharedPrefs.setNamaKonter("");
        sharedPrefs.setKodeSPG("");
        sharedPrefs.setNamaSPG("");
        sharedPrefs.setToken("");

        sharedPrefs.setLatestSyncDownloadDate(0);
        sharedPrefs.setLatestSyncUploadDate(0);
    }

    public void checkSession() {
        setValue(LoginViewState.LOADING_STATE);
        try {
            getDisposable().add(getSession()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(userUiModel -> {
                                LoginViewState.LOGIN_VALID_STATE.setData(userUiModel);
                                postValue(LoginViewState.LOGIN_VALID_STATE);
                            },
                            throwable -> {
                                logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                                LoginViewState.ERROR_STATE.setError(throwable);
                                postValue(LoginViewState.ERROR_STATE);
                            }));
        } catch (Exception throwable) {
            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
            LoginViewState.ERROR_STATE.setError(throwable);
            setValue(LoginViewState.ERROR_STATE);
        }
    }

    private Flowable<UserUiModel> getSession() throws Exception {
        if (sharedPrefs.getUsername().isEmpty()) {
            throw new Exception(resourceHelper.getResourceString(R.string.invalid_session));
        }
        return Flowable.just(new UserUiModel(sharedPrefs.getKodeSPG(), sharedPrefs.getNamaSPG(), sharedPrefs.getKodeKonter(), sharedPrefs.getNamaKonter()));
    }
}
