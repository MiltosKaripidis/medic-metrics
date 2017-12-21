package com.george.medicmetrics.ui.register;

import android.support.annotation.NonNull;

import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Patient;
import com.george.medicmetrics.ui.base.BasePresenter;

class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private DataSource mDataSource;

    RegisterPresenter(DataSource dataSource) {
        mDataSource = dataSource;
    }


    @Override
    public void register(@NonNull String name, @NonNull String lastName, @NonNull String username, @NonNull String password) {
        if (name.isEmpty()) {
            mView.closeKeyboard();
            mView.showInvalidName();
            return;
        }

        if (lastName.isEmpty()) {
            mView.closeKeyboard();
            mView.showInvalidLastName();
            return;
        }

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

        Patient patient = new Patient(name, lastName, username, password);
        mDataSource.setPatient(patient);
        mView.showRegisterSuccess();
        mView.finish();
    }
}
