package com.erebor.tomkins.pos.viewmodel.base;

import androidx.lifecycle.Observer;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.base.BaseViewState;

import org.junit.Before;
import org.mockito.Mock;

public abstract class BaseViewModelTest<T extends BaseViewModel> extends BaseTest {

    @Mock
    public Observer<BaseViewState> observer;
    public abstract T generateViewModel();

    public T viewModel;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        viewModel = generateViewModel();
        viewModel.getViewState().observeForever(observer);
    }
}
