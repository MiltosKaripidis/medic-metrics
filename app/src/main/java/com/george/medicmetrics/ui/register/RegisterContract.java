package com.george.medicmetrics.ui.register;

import android.support.annotation.NonNull;

import com.george.medicmetrics.ui.base.BaseContract;

interface RegisterContract {

    interface View extends BaseContract.View {

        void closeKeyboard();

        void showInvalidName();

        void showInvalidLastName();

        void showInvalidUsername();

        void showInvalidPassword();

        void showRegisterSuccess();

        void finish();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void register(@NonNull String name,
                      @NonNull String lastName,
                      @NonNull String username,
                      @NonNull String password);
    }
}
