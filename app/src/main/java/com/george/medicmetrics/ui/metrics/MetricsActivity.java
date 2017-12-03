package com.george.medicmetrics.ui.metrics;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.ui.base.BaseActivity;

public class MetricsActivity extends BaseActivity {

    private static final String EXTRA_DEVICE_NAME = "com.george.medicmetrics.ui.metrics.DEVICE_NAME";
    private static final String EXTRA_DEVICE_ADDRESS = "com.george.medicmetrics.ui.metrics.DEVICE_ADDRESS";

    public static Intent newIntent(@NonNull Context context,
                                   @NonNull String deviceName,
                                   @NonNull String deviceAddress) {
        Intent intent = new Intent(context, MetricsActivity.class);
        intent.putExtra(EXTRA_DEVICE_NAME, deviceName);
        intent.putExtra(EXTRA_DEVICE_ADDRESS, deviceAddress);
        return intent;
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        String deviceName = intent.getStringExtra(EXTRA_DEVICE_NAME);
        String deviceAddress = intent.getStringExtra(EXTRA_DEVICE_ADDRESS);

        return MetricsFragment.newInstance(deviceName, deviceAddress);
    }
}
