package com.erebor.tomkins.pos.viewmodel.receive;

import com.erebor.tomkins.pos.base.BaseViewState;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;
import com.erebor.tomkins.pos.data.ui.ProductReceiveUiModel;

import java.util.List;

public class ProductReceiveStockViewState extends BaseViewState<List<TrxTerimaStockModel>> {

    public enum State {
        LOADING("loading"), FOUND("found"), ERROR("error"),
        NOT_FOUND("not_found"), UPDATE_FAILED("update_failed"), UPDATED("updated"), FINISH("finish"), ;
        public String value;

        State(String val) {
            value = val;
        }
    }

    private ProductReceiveStockViewState(String currentState) {
        this.currentState = currentState;
    }

    public static ProductReceiveStockViewState LOADING_STATE = new ProductReceiveStockViewState(State.LOADING.value);
    public static ProductReceiveStockViewState FOUND_STATE = new ProductReceiveStockViewState(State.FOUND.value);
    public static ProductReceiveStockViewState NOT_FOUND_STATE = new ProductReceiveStockViewState(State.NOT_FOUND.value);
    public static ProductReceiveStockViewState ERROR_STATE = new ProductReceiveStockViewState(State.ERROR.value);
    public static ProductReceiveStockViewState UPDATED_STATE = new ProductReceiveStockViewState(State.UPDATED.value);
    public static ProductReceiveStockViewState UPDATE_FAILED_STATE = new ProductReceiveStockViewState(State.UPDATE_FAILED.value);
    public static ProductReceiveStockViewState FINISH_STATE = new ProductReceiveStockViewState(State.FINISH.value);
}
