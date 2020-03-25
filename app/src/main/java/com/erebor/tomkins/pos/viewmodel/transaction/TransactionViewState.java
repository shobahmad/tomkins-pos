package com.erebor.tomkins.pos.viewmodel.transaction;

import com.erebor.tomkins.pos.base.BaseViewState;
import com.erebor.tomkins.pos.data.ui.TransactionUiModel;

public class TransactionViewState extends BaseViewState<TransactionUiModel> {

    public enum State {
        LOADING("loading"), NOT_FOUND("not_found"), FOUND("found"), ERROR("error");
        public String value;

        State(String val) {
            value = val;
        }
    }

    private TransactionViewState(String currentState) {
        this.currentState = currentState;
    }

    public static TransactionViewState LOADING_STATE = new TransactionViewState(State.LOADING.value);
    public static TransactionViewState NOT_FOUND_STATE = new TransactionViewState(State.NOT_FOUND.value);
    public static TransactionViewState FOUND_STATE = new TransactionViewState(State.FOUND.value);
    public static TransactionViewState ERROR_STATE = new TransactionViewState(State.ERROR.value);
}
