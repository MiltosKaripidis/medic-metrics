package com.george.medicmetrics.ui.scan;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.ui.base.BaseActivity;

public class ScanDeviceActivity extends BaseActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return ScanDeviceFragment.newInstance();
    }
}
