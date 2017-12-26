package com.george.medicmetrics.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.injection.Injection;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.dashboard.DashboardActivity;
import com.george.medicmetrics.ui.login.LoginActivity;

public class SplashFragment extends BaseFragment<SplashContract.Presenter> implements SplashContract.View {

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @NonNull
    @Override
    protected SplashContract.Presenter createPresenter() {
        DataSource dataSource = Injection.provideDataSource(getContext());
        Handler handler = new Handler();
        return new SplashPresenter(dataSource, handler);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.handleUser();
    }

    @Override
    public void openDashboard() {
        Intent intent = DashboardActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void openLogin() {
        Intent intent = LoginActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
