package com.george.medicmetrics.ui.base;

import android.app.Service;
import android.support.annotation.NonNull;

public abstract class BaseService<T extends BaseContract.Presenter> extends Service implements BaseContract.View {

    protected T mPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @NonNull
    protected abstract T createPresenter();
}
