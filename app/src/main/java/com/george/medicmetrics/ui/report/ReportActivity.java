package com.george.medicmetrics.ui.report;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.ui.base.BaseActivity;

public class ReportActivity extends BaseActivity {

    private static final String EXTRA_RECORD_ID = "com.george.medicmetrics.ui.report.extra.RECORD_ID";

    public static Intent newIntent(Context context, int recordId) {
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra(EXTRA_RECORD_ID, recordId);
        return intent;
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        int recordId = getIntent().getIntExtra(EXTRA_RECORD_ID, -1);
        return ReportFragment.newInstance(recordId);
    }
}
