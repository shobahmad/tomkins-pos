package com.erebor.tomkins.pos.viewmodel.login;

import com.erebor.tomkins.pos.base.BaseViewState;
import com.erebor.tomkins.pos.data.ui.UserUiModel;

public class LoginViewState extends BaseViewState<UserUiModel> {

    public enum State {
        LOADING("loading"), LOGIN_VALID("valid"), ERROR("error"), LOGOUT_VALID("logout");
        public String value;

        State(String val) {
            value = val;
        }
    }

    private LoginViewState(String currentState) {
        this.currentState = currentState;
    }

    public static LoginViewState LOADING_STATE = new LoginViewState(State.LOADING.value);
    public static LoginViewState LOGIN_VALID_STATE = new LoginViewState(State.LOGIN_VALID.value);
    public static LoginViewState LOGOUT_VALID_STATE = new LoginViewState(State.LOGOUT_VALID.value);
    public static LoginViewState ERROR_STATE = new LoginViewState(State.ERROR.value);
}
