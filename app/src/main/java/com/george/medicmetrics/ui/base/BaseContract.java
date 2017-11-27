package com.george.medicmetrics.ui.base;

import android.support.annotation.NonNull;

public interface BaseContract {

    interface View {
        // Empty
    }

    interface Presenter<T extends BaseContract.View> {

        void attachView(@NonNull T view);

        void detachView();
    }
}
