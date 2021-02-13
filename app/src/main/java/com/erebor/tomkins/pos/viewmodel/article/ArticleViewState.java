package com.erebor.tomkins.pos.viewmodel.article;

import com.erebor.tomkins.pos.base.BaseViewState;
import com.erebor.tomkins.pos.data.ui.ArticleUiModel;
import com.erebor.tomkins.pos.data.ui.TransactionUiModel;

import java.util.List;

public class ArticleViewState extends BaseViewState<List<ArticleUiModel>> {

    public enum State {
        LOADING("loading"),
        NOT_FOUND("not_found"),
        FOUND("found"),
        ERROR("error");
        public String value;

        State(String val) {
            value = val;
        }
    }

    private ArticleViewState(String currentState) {
        this.currentState = currentState;
    }

    public static ArticleViewState LOADING_STATE = new ArticleViewState(State.LOADING.value);
    public static ArticleViewState NOT_FOUND_STATE = new ArticleViewState(State.NOT_FOUND.value);
    public static ArticleViewState FOUND_STATE = new ArticleViewState(State.FOUND.value);
    public static ArticleViewState ERROR_STATE = new ArticleViewState(State.ERROR.value);
}
