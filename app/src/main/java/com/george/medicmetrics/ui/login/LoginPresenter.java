package com.george.medicmetrics.ui.login;

import android.support.annotation.NonNull;

import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Patient;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.List;

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

        mDataSource.getPatientList(new Callback<List<Patient>>() {
            @Override
            public void onSuccess(@NonNull List<Patient> patientList) {
                boolean validUser = isUserRegistered(username, password, patientList);
                if (validUser) {
                    mView.openDashboard();
                    mDataSource.setUserLoggedIn();
                    mView.finish();
                } else {
                    mView.closeKeyboard();
                    mView.showInvalidUser();
                }
            }

            @Override
            public void onFailure() {
                // Do nothing
            }
        });
    }

    private boolean isUserRegistered(@NonNull String username, @NonNull String password, List<Patient> patientList) {
        for (Patient patient : patientList) {
            if (patient.getUsername().equals(username) && patient.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
