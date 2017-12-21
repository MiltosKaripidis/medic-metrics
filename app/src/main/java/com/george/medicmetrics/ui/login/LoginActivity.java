package com.george.medicmetrics.ui.login;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.george.medicmetrics.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }
}
