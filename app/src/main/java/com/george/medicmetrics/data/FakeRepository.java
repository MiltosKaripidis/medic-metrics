package com.george.medicmetrics.data;

import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Patient;
import com.george.medicmetrics.objects.Record;

import java.util.List;

public class FakeRepository implements DataSource {

    private static FakeRepository sInstance;

    private FakeRepository() {
        // No instance
    }

    public static FakeRepository getInstance() {
        if (sInstance == null) {
            sInstance = new FakeRepository();
        }

        return sInstance;
    }

    @Override
    public void setPatientId(int patientId) {
        // TODO: Implement
    }

    @Override
    public int getPatientId() {
        return 0;
    }

    @Override
    public boolean isUserLoggedIn() {
        return false;
    }

    @Override
    public void validateUser(@NonNull String username, @NonNull String password, @NonNull Callback<Integer> callback) {
        // TODO: Implement
    }

    @Override
    public void setPatient(@NonNull Patient patient) {
        // Do nothing
    }

    @Override
    public void setRecord(int patientId, @NonNull Record record) {
        // TODO: Implement
    }

    @Override
    public void getRecordList(int patientId, @NonNull Callback<List<Record>> callback) {
        // TODO: Implement
    }

    @Override
    public void deletePatient(int patientId) {
        // TODO: Implement
    }
}
