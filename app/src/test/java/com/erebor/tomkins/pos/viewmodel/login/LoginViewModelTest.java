package com.erebor.tomkins.pos.viewmodel.login;

import com.erebor.tomkins.pos.data.remote.LoginRequest;
import com.erebor.tomkins.pos.data.remote.LoginResponse;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.viewmodel.base.BaseViewModelTest;

import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Flowable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginViewModelTest extends BaseViewModelTest<LoginViewModel> {

    @Mock
    TomkinsService service;
    @Mock
    SharedPrefs sharedPrefs;

    @Override
    public LoginViewModel generateViewModel() {
        return new LoginViewModel(service, logger, sharedPrefs);
    }

    @Test
    public void postLoginTest() {
        LoginResponse loginResponse = new LoginResponse();
        LoginResponse.Result result = loginResponse.new Result();
        loginResponse.setResult(result);
        when(service.postLogin(any(LoginRequest.class))).thenReturn(Flowable.just(loginResponse));

        viewModel.postLogin("", "");

        verify(observer).onChanged(LoginViewState.LOADING_STATE);
        verify(observer).onChanged(LoginViewState.LOGIN_VALID_STATE);
    }
    @Test
    public void getLoginTest() {
        LoginResponse loginResponse = new LoginResponse();
        LoginResponse.Result result = loginResponse.new Result();
        loginResponse.setResult(result);
        when(service.gettLogin(anyString(), anyString())).thenReturn(Flowable.just(loginResponse));

        viewModel.getLogin("", "");

        verify(observer).onChanged(LoginViewState.LOADING_STATE);
        verify(observer).onChanged(LoginViewState.LOGIN_VALID_STATE);
    }
}