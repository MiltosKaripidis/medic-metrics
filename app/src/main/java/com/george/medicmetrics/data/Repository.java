package com.george.medicmetrics.data;

import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Patient;

import java.util.List;

public class Repository implements DataSource {

    private LocalRepository mLocalRepository;
    private static Repository sInstance;
    private boolean mUserLoggedIn;

    private Repository(LocalRepository localRepository) {
        mLocalRepository = localRepository;
    }

    public static Repository getInstance(LocalRepository localRepository) {
        if (sInstance == null) {
            sInstance = new Repository(localRepository);
        }
        return sInstance;
    }

    @Override
    public void setUserLoggedIn() {
        mUserLoggedIn = true;
    }

    @Override
    public boolean isUserLoggedIn() {
        return mUserLoggedIn;
    }

    @Override
    public void login(@NonNull String username, @NonNull String password, @NonNull Callback<Integer> callback) {
        int patientId = mLocalRepository.getPatient(username, password);
        if (patientId == -1) {
            callback.onFailure();
            return;
        }

        callback.onSuccess(patientId);
    }

    @Override
    public void setPatient(@NonNull Patient patient) {
        mLocalRepository.savePatient(patient);
    }

    @Override
    public void getPatientList(@NonNull Callback<List<Patient>> callback) {
        // TODO: Implement
    }
}
