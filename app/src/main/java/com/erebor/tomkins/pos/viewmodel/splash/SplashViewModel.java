package com.erebor.tomkins.pos.viewmodel.splash;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends BaseViewModel<SplashViewState> {

    private static final String TAG = "SplashViewModel";
    private Logger logger;
    private SharedPrefs sharedPrefs;

    @Inject
    public SplashViewModel(Logger logger, SharedPrefs sharedPrefs) {
        this.logger = logger;
        this.sharedPrefs = sharedPrefs;
    }

    public void sessionCheck() {
        SplashViewState.LOADING_STATE.setProgress(10);
        setValue(SplashViewState.LOADING_STATE);

        getDisposable().add(sessionCheckChain()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        state -> postValue(state),
                        throwable -> logger.error(TAG, throwable.getMessage(), throwable)
                ));
    }

    private Flowable<Boolean> isValidSession() {
        SplashViewState.LOADING_STATE.setProgress(10);
        logger.debug(TAG, "sessionCheck... username " + sharedPrefs.getUsername());
        logger.debug(TAG, "sessionCheck... password " + sharedPrefs.getPassword());
        if (!sharedPrefs.getUsername().isEmpty() && !sharedPrefs.getPassword().isEmpty()) {
            logger.debug(TAG, "sessionCheck... Valid session");
            return Flowable.just(true);
        }

        return Flowable.just(false);
    }

    private Flowable<SplashViewState> sessionCheckChain() {
        return isValidSession().flatMap(valid -> {

            SplashViewState.LOADING_STATE.setProgress(30);
            postValue(SplashViewState.LOADING_STATE);
            Thread.sleep(250);
            SplashViewState.LOADING_STATE.setProgress(60);
            postValue(SplashViewState.LOADING_STATE);
            Thread.sleep(250);

            if (valid) {
                SplashViewState.SESSION_VALID_STATE.setProgress(100);
                return Flowable.just(SplashViewState.SESSION_VALID_STATE);
            }

            SplashViewState.LOADING_STATE.setProgress(65);
            postValue(SplashViewState.LOADING_STATE);
            Thread.sleep(250);
            SplashViewState.LOADING_STATE.setProgress(80);
            postValue(SplashViewState.LOADING_STATE);
            Thread.sleep(250);

            SplashViewState.SESSION_EMPTY_STATE.setProgress(100);
            return Flowable.just(SplashViewState.SESSION_EMPTY_STATE);
        });
    }
}
