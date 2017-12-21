package com.george.medicmetrics.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.ui.base.BaseActivity;

public class DashboardActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, DashboardActivity.class);
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        return DashboardFragment.newInstance();
    }
}
