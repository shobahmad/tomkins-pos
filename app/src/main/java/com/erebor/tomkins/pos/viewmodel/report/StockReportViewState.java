package com.erebor.tomkins.pos.viewmodel.report;

import com.erebor.tomkins.pos.base.BaseViewState;
import com.erebor.tomkins.pos.data.local.model.StockReportModel;
import com.erebor.tomkins.pos.data.ui.UserUiModel;

import java.util.List;

public class StockReportViewState extends BaseViewState<List<StockReportModel>> {

    public enum State {
        LOADING("loading"), FOUND("found"), ERROR("error"), NOT_FOUND("not_found");
        public String value;

        State(String val) {
            value = val;
        }
    }

    private StockReportViewState(String currentState) {
        this.currentState = currentState;
    }

    public static StockReportViewState LOADING_STATE = new StockReportViewState(State.LOADING.value);
    public static StockReportViewState FOUND_STATE = new StockReportViewState(State.FOUND.value);
    public static StockReportViewState NOT_FOUND_STATE = new StockReportViewState(State.NOT_FOUND.value);
    public static StockReportViewState ERROR_STATE = new StockReportViewState(State.ERROR.value);
}
