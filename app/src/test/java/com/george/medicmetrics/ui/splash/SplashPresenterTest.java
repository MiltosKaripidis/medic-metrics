package com.george.medicmetrics.ui.splash;

import android.os.Handler;

import com.george.medicmetrics.data.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    private SplashPresenter mPresenter;

    @Mock
    private DataSource mDataSource;

    @Mock
    private Handler mHandler;

    @Mock
    private SplashContract.View mView;

    @Captor
    private ArgumentCaptor<Runnable> mRunnableArgumentCaptor;

    @Before
    public void setupPresenter() {
        mPresenter = new SplashPresenter(mDataSource, mHandler);
        mPresenter.attachView(mView);
    }

    @Test
    public void handleUser_opensDashboard_whenLoggedIn() {
        when(mDataSource.isUserLoggedIn()).thenReturn(true);

        mPresenter.handleUser();

        verify(mHandler).postDelayed(mRunnableArgumentCaptor.capture(), anyLong());
        mRunnableArgumentCaptor.getValue().run();
        verify(mView).openDashboard();
        verify(mView).finish();
        verify(mView, never()).openLogin();
    }

    @Test
    public void handleUser_opensLogin_whenLoggedOff() {
        when(mDataSource.isUserLoggedIn()).thenReturn(false);

        mPresenter.handleUser();

        verify(mHandler).postDelayed(mRunnableArgumentCaptor.capture(), anyLong());
        mRunnableArgumentCaptor.getValue().run();
        verify(mView).openLogin();
        verify(mView).finish();
        verify(mView, never()).openDashboard();
    }
}
