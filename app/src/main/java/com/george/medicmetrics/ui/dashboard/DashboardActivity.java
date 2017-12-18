package com.george.medicmetrics.ui.dashboard;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.ui.base.BaseActivity;

public class DashboardActivity extends BaseActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return DashboardFragment.newInstance();
    }
}
