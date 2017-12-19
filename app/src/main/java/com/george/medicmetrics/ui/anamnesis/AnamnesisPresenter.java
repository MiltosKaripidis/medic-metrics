package com.george.medicmetrics.ui.anamnesis;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Patient;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

class AnamnesisPresenter extends BasePresenter<AnamnesisContract.View> implements AnamnesisContract.Presenter {

    private DataSource mDataSource;

    AnamnesisPresenter(DataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void loadAnamnesis(final int patientId) {
        mDataSource.getPatientList(new Callback<List<Patient>>() {
            @Override
            public void onSuccess(@NonNull List<Patient> patientList) {
                if (patientList.isEmpty()) {
                    mView.showEmptyAnamnesis();
                    return;
                }

                Patient patient = getPatient(patientId, patientList);

                List<Record> recordList = new ArrayList<>();
                mView.showAnamnesis(patient == null ? recordList : patient.getRecordList());
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
}
