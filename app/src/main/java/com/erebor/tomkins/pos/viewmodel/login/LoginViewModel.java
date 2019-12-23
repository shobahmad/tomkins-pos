package com.erebor.tomkins.pos.viewmodel.login;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.remote.LoginRequest;
import com.erebor.tomkins.pos.data.ui.UserUiModel;
import com.erebor.tomkins.pos.repository.network.LoginRepository;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginViewState> {

    private Logger logger;
    private SharedPrefs sharedPrefs;
    private LoginRepository loginRepository;

    @Inject
    public LoginViewModel(LoginRepository loginRepository, Logger logger, SharedPrefs sharedPrefs) {
        this.loginRepository = loginRepository;
        this.logger = logger;
        this.sharedPrefs = sharedPrefs;
    }


    public void postLogin(String username, String password) {
        setValue(LoginViewState.LOADING_STATE);

//        getDisposable().add(service.postLogin(new LoginRequest(username, password))
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(loginResponse -> {
//                            sharedPrefs.setUsername(username);
//                            sharedPrefs.setPassword(password);
//
//                            UserUiModel userUiModel = new UserUiModel(loginResponse.getResult().getId(), loginResponse.getResult().getUsername(), loginResponse.getResult().getName(), loginResponse.getResult().getPosition());
//                            LoginViewState.LOGIN_VALID_STATE.setData(userUiModel);
//                            postValue(LoginViewState.LOGIN_VALID_STATE);
//                        },
//                        throwable -> {
//                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
//                            LoginViewState.ERROR_STATE.setError(throwable);
//                            postValue(LoginViewState.ERROR_STATE);
//                        })
//        );
    }

    public void getLogin(String username, String password) {
        setValue(LoginViewState.LOADING_STATE);

//        getDisposable().add(service.gettLogin(username, password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(loginResponse -> {
//                            sharedPrefs.setUsername(username);
//                            sharedPrefs.setPassword(password);
//
//                            UserUiModel userUiModel = new UserUiModel(loginResponse.getResult().getId(), loginResponse.getResult().getUsername(), loginResponse.getResult().getName(), loginResponse.getResult().getPosition());
//                            LoginViewState.LOGIN_VALID_STATE.setData(userUiModel);
//                            postValue(LoginViewState.LOGIN_VALID_STATE);
//                        },
//                        throwable -> {
//                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
//                            LoginViewState.ERROR_STATE.setError(throwable);
//                            postValue(LoginViewState.ERROR_STATE);
//                        })
//        );

        getDisposable().add(Single.fromCallable(() -> loginRepository.getSyncLogin(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(loginResponse -> {
                            sharedPrefs.setUsername(username);
                            sharedPrefs.setPassword(password);

                            UserUiModel userUiModel = new UserUiModel(loginResponse.getResult().getId(), loginResponse.getResult().getUsername(), loginResponse.getResult().getName(), loginResponse.getResult().getPosition());
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
