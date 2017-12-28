package com.george.medicmetrics.ui.parameters;

import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BaseContract;

interface ParametersContract {

    interface View extends BaseContract.View {

        void showInvalidRespiratoryRate();

        void showInvalidMilliliterPerHour();

        void openScore(@NonNull Record record);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void calculateScore(@NonNull Record record,
                            @NonNull String respiratoryRate,
                            boolean catheterUsed,
                            @NonNull String milliliterPerHour,
                            boolean oxygenSupplemented,
                            @NonNull String consciousnessLevel);
    }
}
