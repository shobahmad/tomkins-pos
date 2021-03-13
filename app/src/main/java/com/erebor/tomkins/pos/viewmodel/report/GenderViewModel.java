package com.erebor.tomkins.pos.viewmodel.report;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.MsGenderDao;
import com.erebor.tomkins.pos.tools.Logger;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class GenderViewModel extends BaseViewModel<GenderViewState> {

    private final MsGenderDao genderDao;
    private final Logger logger;

    @Inject
    public GenderViewModel(MsGenderDao genderDao, Logger logger) {
        this.genderDao = genderDao;
        this.logger = logger;
    }


    public void loadAllGender() {
        setValue(GenderViewState.LOADING_STATE);
        getDisposable().add(Single.fromCallable(genderDao::getListAll)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(result ->  {
                            if (result == null) {
                                postValue(GenderViewState.NOT_FOUND_STATE);
                                return;
                            }
                            GenderViewState.FOUND_STATE.setData(result);
                            postValue(GenderViewState.FOUND_STATE);
                        },
                        throwable -> {
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            GenderViewState.ERROR_STATE.setError(throwable);
                            postValue(GenderViewState.ERROR_STATE);
                        }));
    }

}
