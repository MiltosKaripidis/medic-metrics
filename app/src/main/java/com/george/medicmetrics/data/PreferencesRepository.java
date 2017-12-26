package com.george.medicmetrics.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesRepository {

    private static final String PREF_PATIENT_ID = "patient_id";
    private SharedPreferences mSharedPreferences;

    public PreferencesRepository(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    void setPatientId(int patientId) {
        mSharedPreferences.edit()
                .putInt(PREF_PATIENT_ID, patientId)
                .apply();
    }

    int getPatientId() {
        return mSharedPreferences.getInt(PREF_PATIENT_ID, -1);
    }
}
