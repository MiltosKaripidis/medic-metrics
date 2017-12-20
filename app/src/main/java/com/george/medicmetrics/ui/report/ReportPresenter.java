package com.george.medicmetrics.ui.report;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Patient;
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
    public void loadRecord(final int patientId, final int recordId) {
        mDataSource.getPatientList(new Callback<List<Patient>>() {
            @Override
            public void onSuccess(@NonNull List<Patient> patientList) {
                Patient patient = getPatient(patientId, patientList);
                if (patient == null) return;
                Record record = getRecord(recordId, patient.getRecordList());
                if (record == null) return;

                showMetrics(record);
                changeCardColor(record.getScore());
            }
        });
    }

    @Nullable
    private Patient getPatient(int patientId, @NonNull List<Patient> patientList) {
        for (Patient patient : patientList) {
            if (patient.getId() != patientId) {
                continue;
            }

            return patient;
        }

        return null;
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
        String score = String.valueOf(record.getScore());
        mView.showScore(score);
        mView.showTimestamp(record.getTimestamp());
    }

    private void changeCardColor(int score) {
        if (score == 3 || score == 4) {
            mView.changeCardColor(R.color.green);
        } else if (score == 5 || score == 6) {
            mView.changeCardColor(R.color.orange);
        } else if (score >= 7) {
            mView.changeCardColor(R.color.red);
        }
    }
}
