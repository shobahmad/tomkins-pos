package com.erebor.tomkins.pos.viewmodel.splash

import com.erebor.tomkins.pos.base.BaseViewState

class SplashViewState : BaseViewState<String> {

    var progress = 0
    var message = ""

    enum class State private constructor(var value: Int) {
        LOADING(0), SESSION_VALID(1), SESSION_EMPTY(2), ERROR(-1)
    }

    private constructor(currentState: Int) : super("") {
        this.currentState = currentState
    }

    constructor(progress: Int, message: String) : super(message) {
        this.progress = progress
        this.message = message
    }

    companion object {

        var LOADING_STATE = SplashViewState(State.LOADING.value)
        var SESSION_VALID_STATE = SplashViewState(State.SESSION_VALID.value)
        var SESSION_EMPTY_STATE = SplashViewState(State.SESSION_EMPTY.value)
        var ERROR_STATE = SplashViewState(State.ERROR.value, "")
    }
}
