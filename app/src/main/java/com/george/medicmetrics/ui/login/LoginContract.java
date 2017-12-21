package com.george.medicmetrics.ui.login;

import android.support.annotation.NonNull;

import com.george.medicmetrics.ui.base.BaseContract;

interface LoginContract {

    interface View extends BaseContract.View {

        void showInvalidUsername();

        void showInvalidPassword();

        void showInvalidUser();

        void closeKeyboard();

        void openDashboard();

        void openRegister();

        void finish();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void login(@NonNull String username, @NonNull String password);
    }
}
