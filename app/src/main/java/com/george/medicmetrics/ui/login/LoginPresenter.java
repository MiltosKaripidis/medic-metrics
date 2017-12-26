package com.george.medicmetrics.ui.login;

import android.support.annotation.NonNull;

import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.ui.base.BasePresenter;

class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private DataSource mDataSource;

    LoginPresenter(DataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void login(@NonNull final String username, @NonNull final String password) {
        if (username.isEmpty()) {
            mView.closeKeyboard();
            mView.showInvalidUsername();
            return;
        }

        if (password.isEmpty()) {
            mView.closeKeyboard();
            mView.showInvalidPassword();
            return;
        }

        mDataSource.validateUser(username, password, new Callback<Integer>() {
            @Override
            public void onSuccess(@NonNull Integer patientId) {
                mView.openDashboard();
                mDataSource.setPatientId(patientId);
                mView.finish();
            }

            @Override
            public void onFailure() {
                mView.closeKeyboard();
                mView.showInvalidUser();
            }
        });
    }
}
