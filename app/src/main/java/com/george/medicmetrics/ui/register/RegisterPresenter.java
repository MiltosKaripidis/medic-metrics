package com.george.medicmetrics.ui.register;

import android.support.annotation.NonNull;

import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Patient;
import com.george.medicmetrics.ui.base.BasePresenter;

class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private DataSource mDataSource;

    RegisterPresenter(DataSource dataSource) {
        mDataSource = dataSource;
    }


    @Override
    public void register(@NonNull final String name, @NonNull final String lastName, @NonNull final String username, @NonNull final String password) {
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

        mDataSource.validateUser(username, password, new Callback<Integer>() {
            @Override
            public void onSuccess(@NonNull Integer patientId) {
                if (patientId != -1) {
                    mView.closeKeyboard();
                    mView.showUserExists();
                    return;
                }

                Patient patient = new Patient(name, lastName, username, password);
                mDataSource.setPatient(patient);
                mView.showRegisterSuccess();
                mView.finish();
            }

            @Override
            public void onFailure() {
                // Do nothing.
            }
        });
    }
}
