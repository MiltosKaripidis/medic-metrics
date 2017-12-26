package com.george.medicmetrics.ui.splash;

import android.os.Handler;

import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.ui.base.BasePresenter;

class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    private static final int DELAY_IN_MILLIS = 2000;
    private DataSource mDataSource;
    private Handler mHandler;

    SplashPresenter(DataSource dataSource, Handler handler) {
        mDataSource = dataSource;
        mHandler = handler;
    }

    @Override
    public void handleUser() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDataSource.isUserLoggedIn()) {
                    mView.openDashboard();
                    mView.finish();
                    return;
                }

                mView.openLogin();
                mView.finish();
            }
        }, DELAY_IN_MILLIS);
    }
}
