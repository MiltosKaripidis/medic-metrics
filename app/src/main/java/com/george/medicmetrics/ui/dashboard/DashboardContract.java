package com.george.medicmetrics.ui.dashboard;

import com.george.medicmetrics.ui.base.BaseContract;

interface DashboardContract {

    interface View extends BaseContract.View {

        void openScan();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void handleClick(int tileId);
    }
}
