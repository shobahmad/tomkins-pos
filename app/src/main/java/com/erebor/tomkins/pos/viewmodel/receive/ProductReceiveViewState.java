package com.erebor.tomkins.pos.viewmodel.receive;

import com.erebor.tomkins.pos.base.BaseViewState;
import com.erebor.tomkins.pos.data.local.model.StockReportModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.ui.ProductReceiveUiModel;

import java.util.List;

public class ProductReceiveViewState extends BaseViewState<List<ProductReceiveUiModel>> {

    public enum State {
        LOADING("loading"), FOUND("found"), ERROR("error"), NOT_FOUND("not_found");
        public String value;

        State(String val) {
            value = val;
        }
    }

    private ProductReceiveViewState(String currentState) {
        this.currentState = currentState;
    }

    public static ProductReceiveViewState LOADING_STATE = new ProductReceiveViewState(State.LOADING.value);
    public static ProductReceiveViewState FOUND_STATE = new ProductReceiveViewState(State.FOUND.value);
    public static ProductReceiveViewState NOT_FOUND_STATE = new ProductReceiveViewState(State.NOT_FOUND.value);
    public static ProductReceiveViewState ERROR_STATE = new ProductReceiveViewState(State.ERROR.value);
}
