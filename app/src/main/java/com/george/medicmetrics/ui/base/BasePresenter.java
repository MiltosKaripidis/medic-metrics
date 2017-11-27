package com.george.medicmetrics.ui.base;

import android.support.annotation.NonNull;

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter<T> {

    protected T mView;

    @Override
    public void attachView(@NonNull T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
