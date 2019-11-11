package com.erebor.tomkins.pos.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel<T : BaseViewState<*>> : ViewModel() {

    val viewState: MutableLiveData<T>
    var disposable: CompositeDisposable? = null
        private set

    init {
        this.disposable = CompositeDisposable()
        this.viewState = MutableLiveData()
    }

    fun postValue(state: T) {
        viewState.postValue(state)
    }

    fun setValue(state: T) {
        viewState.value = state
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }
}
