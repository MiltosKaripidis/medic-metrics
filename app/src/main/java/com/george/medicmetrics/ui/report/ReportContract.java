package com.george.medicmetrics.ui.report;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.george.medicmetrics.ui.base.BaseContract;

interface ReportContract {

    interface View extends BaseContract.View {

        void showRespiratoryRate(@NonNull String bpm);

        void showBloodOxygen(@NonNull String percent);

        void showBodyTemperature(@NonNull String celsius);

        void showSystolicBloodPressure(@NonNull String pressure);

        void showHeartRate(@NonNull String bpm);

        void showUrineOutput(@NonNull String milliliterPerHour);

        void showOxygenSupplemented(@NonNull String oxygenSupplemented);

        void showConsciousnessLevel(@NonNull String consciousnessLevel);

        void showDescription(@StringRes int description);

        void showScore(@NonNull String score);

        void showTimestamp(@NonNull String timestamp);

        void changeCardColor(int colorId);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void loadRecord(int recordId);
    }
}
