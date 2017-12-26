package com.george.medicmetrics.ui.splash;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return SplashFragment.newInstance();
    }
}
