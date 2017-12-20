package com.george.medicmetrics.ui.report;

import com.george.medicmetrics.ui.base.BaseContract;

interface ReportContract {

    interface View extends BaseContract.View {

        void showRespiratoryRate(String bpm);

        void showBloodOxygen(String percent);

        void showBodyTemperature(String celsius);

        void showSystolicBloodPressure(String pressure);

        void showHeartRate(String bpm);

        void showScore(String score);

        void showTimestamp(String timestamp);

        void changeCardColor(int colorId);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void loadRecord(int patientId, int recordId);
    }
}
