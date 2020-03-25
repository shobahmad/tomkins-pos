package com.erebor.tomkins.pos.viewmodel.sync;

import com.erebor.tomkins.pos.base.BaseViewState;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;

import java.util.List;

public class DownloadInfoViewState extends BaseViewState<List<DownloadUiModel>> {
    private static String STATE_LOADING = "loading";
    private static String STATE_SUCCESS = "success";
    private static String STATE_ERROR = "error";



    public enum State {
        LOADING(STATE_LOADING), SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR);
        public String value;

        State(String val) {
            value = val;
        }
    }

    private DownloadInfoViewState(String currentState) {
        this.currentState = currentState;
    }

    public static DownloadInfoViewState SUCCESS_STATE = new DownloadInfoViewState(State.SUCCESS.value);
    public static DownloadInfoViewState ERROR_STATE = new DownloadInfoViewState(State.ERROR.value);
    public static DownloadInfoViewState LOADING_STATE = new DownloadInfoViewState(State.LOADING.value);

}
