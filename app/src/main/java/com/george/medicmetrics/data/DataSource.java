package com.george.medicmetrics.data;

import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Patient;
import com.george.medicmetrics.objects.Record;

import java.util.List;

public interface DataSource {

    void setPatientId(int patientId);

    int getPatientId();

    boolean isUserLoggedIn();

    void validateUser(@NonNull String username, @NonNull String password, @NonNull Callback<Integer> callback);

    void setPatient(@NonNull Patient patient);

    void getPatientList(@NonNull Callback<List<Patient>> callback);

    void setRecord(int patientId, @NonNull Record record);

    void getRecordList(int patientId, @NonNull Callback<List<Record>> callback);
}
