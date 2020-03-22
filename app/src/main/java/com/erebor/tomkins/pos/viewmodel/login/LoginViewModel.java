package com.erebor.tomkins.pos.viewmodel.login;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.remote.LoginRequest;
import com.erebor.tomkins.pos.data.ui.UserUiModel;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginViewState> {

    private Logger logger;
    private SharedPrefs sharedPrefs;
    private TomkinsService service;

    @Inject
    public LoginViewModel(TomkinsService service, Logger logger, SharedPrefs sharedPrefs) {
        this.service = service;
        this.logger = logger;
        this.sharedPrefs = sharedPrefs;
    }


    public void postLogin(String username, String password) {
        setValue(LoginViewState.LOADING_STATE);

        getDisposable().add(service.postLogin(new LoginRequest(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(loginResponse -> {
                            sharedPrefs.setUsername(username);
                            sharedPrefs.setPassword(password);
                            sharedPrefs.setKodeKonter(loginResponse.getResult().getKodeKonter());
                            sharedPrefs.setNamaKonter(loginResponse.getResult().getNamaKonter());
                            sharedPrefs.setKodeSPG(loginResponse.getResult().getKodeSPG());
                            sharedPrefs.setNamaSPG(loginResponse.getResult().getNamaSPG());

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
}
