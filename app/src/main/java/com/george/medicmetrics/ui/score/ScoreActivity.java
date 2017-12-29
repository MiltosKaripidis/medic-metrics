package com.george.medicmetrics.ui.score;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BaseActivity;

public class ScoreActivity extends BaseActivity {

    private static final String EXTRA_RECORD = "com.george.medicmetrics.ui.score.extra.RECORD";

    public static Intent newIntent(Context context, Record record) {
        Intent intent = new Intent(context, ScoreActivity.class);
        intent.putExtra(EXTRA_RECORD, record);
        return intent;
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        Record record = (Record) getIntent().getSerializableExtra(EXTRA_RECORD);
        return ScoreFragment.newInstance(record);
    }
}
