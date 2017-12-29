package com.george.medicmetrics.ui.score;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BaseContract;

interface ScoreContract {

    interface View extends BaseContract.View {

        void changeCardColor(@ColorRes int colorId);

        void showRespiratoryRate(@NonNull String bpm);

        void showBloodOxygen(@NonNull String percent);

        void showBodyTemperature(@NonNull String celsius);

        void showSystolicBloodPressure(@NonNull String pressure);

        void showHeartRate(@NonNull String bpm);

        void showUrineOutput(@NonNull String milliliterPerHour);

        void showOxygenSupplemented(@NonNull String oxygenSupplemented);

        void showConsciousnessLevel(@NonNull String consciousnessLevel);

        void showScore(@NonNull String score);

        void showTimestamp(@NonNull String timestamp);

        void openDashboard();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void loadScore(@NonNull Record record);

        void saveRecord(@NonNull Record record);
    }
}
