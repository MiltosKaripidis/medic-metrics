package com.george.medicmetrics.ui.dashboard;

import com.george.medicmetrics.ui.base.BasePresenter;

class DashboardPresenter extends BasePresenter<DashboardContract.View> implements DashboardContract.Presenter {

    private static final int ID_NEW_MEASUREMENT = 1;
    private static final int ID_AMNESIS = 2;
    private static final int ID_ABOUT = 3;

    @Override
    public void handleClick(int tileId) {
        switch (tileId) {
            case ID_NEW_MEASUREMENT:
                mView.openScan();
                break;
            case ID_AMNESIS:
                break;
            case ID_ABOUT:
                break;
        }
    }
}
