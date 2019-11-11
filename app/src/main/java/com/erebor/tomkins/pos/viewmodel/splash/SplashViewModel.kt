package com.erebor.tomkins.pos.viewmodel.splash

import com.erebor.tomkins.pos.base.BaseViewModel
import com.erebor.tomkins.pos.tools.Logger
import com.erebor.tomkins.pos.tools.SharedPrefs

import javax.inject.Inject

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class SplashViewModel @Inject
constructor(private val logger: Logger, private val sharedPrefs: SharedPrefs) : BaseViewModel<SplashViewState>() {

    private val isValidSession: Flowable<Boolean>
        get() {
            SplashViewState.LOADING_STATE.progress = 10
            logger.debug(TAG, "sessionCheck... username " + sharedPrefs.username)
            logger.debug(TAG, "sessionCheck... password " + sharedPrefs.password)
            if (!sharedPrefs.username.isEmpty() && !sharedPrefs.password.isEmpty()) {
                logger.debug(TAG, "sessionCheck... Valid session")
                return Flowable.just(true)
            }

            return Flowable.just(false)
        }

    fun sessionCheck() {
        SplashViewState.LOADING_STATE.progress = 10
        setValue(SplashViewState.LOADING_STATE)

        disposable!!.add(sessionCheckChain()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        { state -> postValue(state) },
                        { throwable -> logger.error(TAG, throwable.message!!, throwable) }
                ))
    }

    private fun sessionCheckChain(): Flowable<SplashViewState> {
        return isValidSession.flatMap { valid ->

            SplashViewState.LOADING_STATE.progress = 30
            postValue(SplashViewState.LOADING_STATE)
            Thread.sleep(250)
            SplashViewState.LOADING_STATE.progress = 60
            postValue(SplashViewState.LOADING_STATE)
            Thread.sleep(250)

            if (valid) {
                SplashViewState.SESSION_VALID_STATE.progress = 100
                return@flatMap Flowable.just(SplashViewState.SESSION_VALID_STATE)
            }

            SplashViewState.LOADING_STATE.progress = 65
            postValue(SplashViewState.LOADING_STATE)
            Thread.sleep(250)
            SplashViewState.LOADING_STATE.progress = 80
            postValue(SplashViewState.LOADING_STATE)
            Thread.sleep(250)

            SplashViewState.SESSION_EMPTY_STATE.progress = 100
            Flowable.just(SplashViewState.SESSION_EMPTY_STATE)
        }
    }

    companion object {

        private val TAG = "SplashViewModel"
    }
}
