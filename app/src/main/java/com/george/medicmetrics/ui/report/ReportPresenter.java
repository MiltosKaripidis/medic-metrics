package com.george.medicmetrics.ui.report;

import android.support.annotation.NonNull;

import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.Locale;

class ReportPresenter extends BasePresenter<ReportContract.View> implements ReportContract.Presenter {

    private DataSource mDataSource;

    ReportPresenter(DataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void loadRecord(final int recordId) {
        final int patientId = mDataSource.getPatientId();
        mDataSource.getRecord(patientId, recordId, new Callback<Record>() {
            @Override
            public void onSuccess(@NonNull Record record) {
                showMetrics(record);
            }

            @Override
            public void onFailure() {
                // Do nothing
            }
        });
    }

    private void showMetrics(Record record) {
        String respiratoryRateString = String.format(Locale.getDefault(), "%.0f", record.getRespiratoryRate());
        mView.showRespiratoryRate(respiratoryRateString);
        String bloodOxygen = String.format(Locale.getDefault(), "%.0f", record.getBloodOxygen());
        mView.showBloodOxygen(bloodOxygen);
        String bodyTemperature = String.format(Locale.getDefault(), "%.1f", record.getBodyTemperature());
        mView.showBodyTemperature(bodyTemperature);
        String systolicBloodPressure = String.format(Locale.getDefault(), "%.0f", record.getSystolicBloodPressure());
        mView.showSystolicBloodPressure(systolicBloodPressure);
        String heartRate = String.format(Locale.getDefault(), "%.0f", record.getHearRate());
        mView.showHeartRate(heartRate);
        if (record.isCatheterUsed()) {
            String milliliterPerHour = String.valueOf(record.getMilliliterPerHour());
            mView.showUrineOutput(milliliterPerHour);
        }
        mView.showOxygenSupplemented(record.isOxygenSupplemented() ? "Yes" : "No");
        mView.showConsciousnessLevel(record.getConsciousnessLevel());
        String score = String.valueOf(record.getScore());
        mView.showScore(score);
        mView.showTimestamp(record.getTimestamp());
        mView.changeCardColor(record.getColor());
        mView.showDescription(record.getDescription());
    }
}
