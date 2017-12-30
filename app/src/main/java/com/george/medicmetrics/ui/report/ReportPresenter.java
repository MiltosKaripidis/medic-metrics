package com.george.medicmetrics.ui.report;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.List;
import java.util.Locale;

class ReportPresenter extends BasePresenter<ReportContract.View> implements ReportContract.Presenter {

    private DataSource mDataSource;

    ReportPresenter(DataSource dataSource) {
        mDataSource = dataSource;
    }


    @Override
    public void loadRecord(final int recordId) {
        final int patientId = mDataSource.getPatientId();
        mDataSource.getRecordList(patientId, new Callback<List<Record>>() {
            @Override
            public void onSuccess(@NonNull List<Record> recordList) {
                Record record = getRecord(recordId, recordList);
                if (record == null) return;

                showMetrics(record);
                changeCardColor(record.getClinicalConcern());
            }

            @Override
            public void onFailure() {
                // Do nothing
            }
        });
    }

    @Nullable
    private Record getRecord(int recordId, List<Record> recordList) {
        for (Record record : recordList) {
            if (record.getId() != recordId) {
                continue;
            }

            return record;
        }

        return null;
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
    }

    private void changeCardColor(int clinicalConcern) {
        switch (clinicalConcern) {
            case Record.CLINICAL_CONCERN_LOW:
                mView.changeCardColor(R.color.green);
                break;
            case Record.CLINICAL_CONCERN_MEDIUM:
                mView.changeCardColor(R.color.orange);
                break;
            case Record.CLINICAL_CONCERN_HIGH:
                mView.changeCardColor(R.color.red);
                break;
        }
    }
}
