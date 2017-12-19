package com.george.medicmetrics.ui.anamnesis;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.ui.base.BaseActivity;

public class AnamnesisActivity extends BaseActivity {

    private static final String EXTRA_PATIENT_ID = "com.george.medicmetrics.ui.anamnesis.extra.PATIENT_ID";

    public static Intent newIntent(Context context, int patientId) {
        Intent intent = new Intent(context, AnamnesisActivity.class);
        intent.putExtra(EXTRA_PATIENT_ID, patientId);
        return intent;
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        int patientId = getIntent().getIntExtra(EXTRA_PATIENT_ID, -1);
        return AnamnesisFragment.newInstance(patientId);
    }
}
