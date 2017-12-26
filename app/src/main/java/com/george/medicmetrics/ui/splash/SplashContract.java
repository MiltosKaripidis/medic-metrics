package com.george.medicmetrics.ui.splash;

import com.george.medicmetrics.ui.base.BaseContract;

interface SplashContract {

    interface View extends BaseContract.View {

        void openDashboard();

        void openLogin();

        void finish();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void handleUser();
    }
}
