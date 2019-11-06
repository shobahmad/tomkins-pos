package com.erebor.tomkins.pos.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<T extends BaseViewState> extends ViewModel {

    private final MutableLiveData<T> viewState;
    private CompositeDisposable disposable;

    public BaseViewModel() {
        this.disposable = new CompositeDisposable();
        this.viewState = new MutableLiveData<>();
    }

    public MutableLiveData<T> getViewState() {
        return viewState;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void postValue(T state) {
        viewState.postValue(state);
    }

    public void setValue(T state) {
        viewState.setValue(state);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
