package com.george.medicmetrics.ui.dashboard;

import android.support.annotation.NonNull;

import com.george.medicmetrics.ui.base.BaseContract;

interface DashboardContract {

    interface View extends BaseContract.View {

        void showFullName(@NonNull String fullName);

        void openScan();

        void openAnamnesis();

        void openLogin();

        void openAbout();

        void openDialog();

        void finish();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void loadUser();

        void handleClick(int tileId);

        void deletePatient();
    }
}
