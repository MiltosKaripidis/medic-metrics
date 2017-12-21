package com.george.medicmetrics.data;

import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Patient;

import java.util.List;

public interface DataSource {

    void setUserLoggedIn();

    boolean isUserLoggedIn();

    void login(@NonNull String username, @NonNull String password, @NonNull Callback<Integer> callback);

    void setPatient(@NonNull Patient patient);

    void getPatientList(@NonNull Callback<List<Patient>> callback);
}
