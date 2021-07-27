package com.erebor.tomkins.pos.viewmodel.splash;

import android.content.ContentResolver;
import android.os.Build;

import com.erebor.tomkins.pos.BuildConfig;
import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends BaseViewModel<SplashViewState> {

    private static final String TAG = "SplashViewModel";
    private final Logger logger;
    private final SharedPrefs sharedPrefs;
    private final DateConverterHelper dateConverterHelper;

    @Inject
    public SplashViewModel(Logger logger, SharedPrefs sharedPrefs, DateConverterHelper dateConverterHelper) {
        this.logger = logger;
        this.sharedPrefs = sharedPrefs;
        this.dateConverterHelper = dateConverterHelper;
    }

    public void sessionCheck(ContentResolver contentResolver) {
        SplashViewState.LOADING_STATE.setProgress(10);
        setValue(SplashViewState.LOADING_STATE);

        getDisposable().add(sessionCheckChain(contentResolver)
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

    private Flowable<SplashViewState> sessionCheckChain(ContentResolver contentResolver) {
        return isValidSession().flatMap(valid -> {

            SplashViewState.LOADING_STATE.setProgress(30);
            postValue(SplashViewState.LOADING_STATE);
            Thread.sleep(250);
            SplashViewState.LOADING_STATE.setProgress(60);
            postValue(SplashViewState.LOADING_STATE);
            Thread.sleep(250);

            if (!isValidTime(contentResolver)) {
                SplashViewState.ERROR_STATE.setProgress(60);
                SplashViewState.ERROR_STATE.setError(new Throwable("Sorry, invalid time. Please set to automatic date & time!"));
                return Flowable.just(SplashViewState.ERROR_STATE);
            }
            if (!isValidApps()) {
                SplashViewState.ERROR_STATE.setProgress(60);
                SplashViewState.ERROR_STATE.setError(new Throwable("Sorry, invalid apps signature.. Please contact developer!"));
                return Flowable.just(SplashViewState.ERROR_STATE);
            }

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

    private boolean isValidTime(ContentResolver contentResolver) {
        boolean autoTime = false;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            autoTime = android.provider.Settings.Global.getInt(contentResolver, android.provider.Settings.Global.AUTO_TIME, 0) == 1;
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            autoTime = android.provider.Settings.System.getInt(contentResolver, android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }

        return autoTime;
    }
    private boolean isValidApps() {
        if (BuildConfig.Expired.equals("")) {
            return true;
        }
        try {
            Date dateNow = Calendar.getInstance().getTime();
            Date expiredApps = dateConverterHelper.toDateFromConfig(BuildConfig.Expired);
            return dateNow.before(expiredApps);
        } catch (ParseException e) {
            return false;
        }
    }
}
