package com.george.medicmetrics.ui.dashboard;

import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.ui.base.BasePresenter;

class DashboardPresenter extends BasePresenter<DashboardContract.View> implements DashboardContract.Presenter {

    private static final int ID_NEW_MEASUREMENT = 1;
    private static final int ID_AMNESIS = 2;
    private static final int ID_ABOUT = 3;
    private static final int ID_LOGOUT = 4;
    private DataSource mDataSource;

    DashboardPresenter(DataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void handleClick(int tileId) {
        switch (tileId) {
            case ID_NEW_MEASUREMENT:
                mView.openScan();
                break;
            case ID_AMNESIS:
                mView.openAnamnesis();
                break;
            case ID_ABOUT:
                break;
            case ID_LOGOUT:
                mDataSource.setPatientId(-1);
                mView.openLogin();
                mView.finish();
                break;
        }
    }
}
