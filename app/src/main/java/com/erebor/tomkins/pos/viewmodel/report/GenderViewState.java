package com.erebor.tomkins.pos.viewmodel.report;

import com.erebor.tomkins.pos.base.BaseViewState;
import com.erebor.tomkins.pos.data.local.model.MsGenderDBModel;
import com.erebor.tomkins.pos.data.local.model.StockReportModel;

import java.util.List;

public class GenderViewState extends BaseViewState<List<MsGenderDBModel>> {

    public enum State {
        LOADING("loading"), FOUND("found"), ERROR("error"), NOT_FOUND("not_found");
        public String value;

        State(String val) {
            value = val;
        }
    }

    private GenderViewState(String currentState) {
        this.currentState = currentState;
    }

    public static GenderViewState LOADING_STATE = new GenderViewState(State.LOADING.value);
    public static GenderViewState FOUND_STATE = new GenderViewState(State.FOUND.value);
    public static GenderViewState NOT_FOUND_STATE = new GenderViewState(State.NOT_FOUND.value);
    public static GenderViewState ERROR_STATE = new GenderViewState(State.ERROR.value);
}
