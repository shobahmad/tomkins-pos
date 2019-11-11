package com.erebor.tomkins.pos.base

abstract class BaseViewState<T>(var data: T) {

    lateinit var error: Throwable
    var currentState: Int = 0

    override fun toString(): String {
        return currentState.toString() + ""
    }
}
