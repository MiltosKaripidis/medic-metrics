package com.george.medicmetrics.ui.register;

import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Patient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegisterTest {

    private static final String EMPTY = "";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final int EXISTING_PATIENT_ID = 1;
    private static final int PATIENT_ID = -1;
    private RegisterPresenter mPresenter;

    @Mock
    private DataSource mDataSource;

    @Mock
    private RegisterContract.View mView;

    @Captor
    private ArgumentCaptor<Callback<Integer>> mCallbackArgumentCaptor;

    @Before
    public void setupPresenter() {
        mPresenter = new RegisterPresenter(mDataSource);
        mPresenter.attachView(mView);
    }

    @Test
    public void register_showsInvalidName_whenNameIsEmpty() {
        mPresenter.register(EMPTY, LAST_NAME, USERNAME, PASSWORD);

        verify(mView).closeKeyboard();
        verify(mView).showInvalidName();
    }

    @Test
    public void register_showsInvalidLastName_whenLastNameIsEmpty() {
        mPresenter.register(FIRST_NAME, EMPTY, USERNAME, PASSWORD);

        verify(mView).closeKeyboard();
        verify(mView).showInvalidLastName();
    }

    @Test
    public void register_showsInvalidUsername_whenUsernameIsEmpty() {
        mPresenter.register(FIRST_NAME, LAST_NAME, EMPTY, PASSWORD);

        verify(mView).closeKeyboard();
        verify(mView).showInvalidUsername();
    }

    @Test
    public void register_showsInvalidPassword_whenPasswordIsEmpty() {
        mPresenter.register(FIRST_NAME, LAST_NAME, USERNAME, EMPTY);

        verify(mView).closeKeyboard();
        verify(mView).showInvalidPassword();
    }

    @Test
    public void register_fails() {
        mPresenter.register(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);

        verify(mDataSource).validateUser(anyString(), anyString(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onFailure();
        verify(mView).showInvalidRegistration();
    }

    @Test
    public void register_showsUserExists_whenUserExists() {
        mPresenter.register(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);

        verify(mDataSource).validateUser(anyString(), anyString(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onSuccess(EXISTING_PATIENT_ID);
        verify(mView).closeKeyboard();
        verify(mView).showUserExists();
    }

    @Test
    public void register_registersUser_whenUserNotExists() {
        mPresenter.register(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);

        verify(mDataSource).validateUser(anyString(), anyString(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onSuccess(PATIENT_ID);
        verify(mDataSource).setPatient(any(Patient.class));
        verify(mView).showRegisterSuccess();
        verify(mView).finish();
    }
}
