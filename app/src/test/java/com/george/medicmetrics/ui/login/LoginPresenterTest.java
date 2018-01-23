package com.george.medicmetrics.ui.login;

import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    private static final String EMPTY = "";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final int PATIENT_ID = 1;
    private LoginPresenter mPresenter;

    @Mock
    private DataSource mDataSource;

    @Mock
    private LoginContract.View mView;

    @Captor
    private ArgumentCaptor<Callback<Integer>> mCallbackArgumentCaptor;

    @Before
    public void setupPresenter() {
        mPresenter = new LoginPresenter(mDataSource);
        mPresenter.attachView(mView);
    }

    @Test
    public void login_showsInvalidUsername_whenUsernameIsEmpty() {
        mPresenter.login(EMPTY, PASSWORD);

        verify(mView).closeKeyboard();
        verify(mView).showInvalidUsername();
    }

    @Test
    public void login_showsInvalidPassword_whenPasswordIsEmpty() {
        mPresenter.login(USERNAME, EMPTY);

        verify(mView).closeKeyboard();
        verify(mView).showInvalidPassword();
    }

    @Test
    public void login_showsInvalidUsername_whenUsernameAndPasswordAreEmpty() {
        mPresenter.login(EMPTY, EMPTY);

        verify(mView).closeKeyboard();
        verify(mView).showInvalidUsername();
    }

    @Test
    public void login_showsInvalidUser_whenFailed() {
        mPresenter.login(USERNAME, PASSWORD);

        verify(mDataSource).validateUser(anyString(), anyString(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onFailure();
        verify(mView).closeKeyboard();
        verify(mView).showInvalidUser();
    }

    @Test
    public void login_opensDashboard() {
        mPresenter.login(USERNAME, PASSWORD);

        verify(mDataSource).validateUser(anyString(), anyString(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onSuccess(PATIENT_ID);
        verify(mView).openDashboard();
        verify(mDataSource).setPatientId(PATIENT_ID);
        verify(mView).finish();
    }
}
