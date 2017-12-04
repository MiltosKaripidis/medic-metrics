package com.george.medicmetrics.ui.scan;

import android.app.Activity;
import android.os.Handler;

import com.george.medicmetrics.behavior.adapter.Adapter;
import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.ui.base.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ScanDevicePresenterTest extends BasePresenterTest {

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final int REQUEST_WRONG = 2;
    private static final Device DEVICE = getDeviceList().get(0);
    private static final List<Device> DEVICE_LIST = new ArrayList<Device>() {{
        add(DEVICE);
    }};
    private ScanDevicePresenter mPresenter;

    @Mock
    private ScanDeviceContract.View mView;

    @Mock
    private Handler mHandler;

    @Mock
    private Adapter mAdapter;

    @Mock
    private Executor mExecutor;

    @Captor
    private ArgumentCaptor<Runnable> mRunnable;

    @Captor
    private ArgumentCaptor<Adapter.ScanDeviceCallback> mScanDeviceCallback;

    @Before
    @Override
    public void setupPresenter() {
        mPresenter = new ScanDevicePresenter(mHandler, mAdapter, mExecutor);
        mPresenter.attachView(mView);
    }

    @Test
    public void handleBluetoothSettingsResult_shouldFinish_whenResultCanceled() {
        mPresenter.handleSettingsResult(REQUEST_ENABLE_BLUETOOTH, Activity.RESULT_CANCELED);
        verify(mView).finish();
    }

    @Test
    public void handleBluetoothSettingsResult_shouldFinish_whenWrongRequestCode() {
        mPresenter.handleSettingsResult(REQUEST_WRONG, Activity.RESULT_OK);
        verify(mView).finish();
    }

    @Test
    public void handleBluetoothSettingsResult_shouldNotFinish_whenCorrectRequestAndResultOk() {
        mPresenter.handleSettingsResult(REQUEST_ENABLE_BLUETOOTH, Activity.RESULT_OK);
        verify(mView, never()).finish();
    }

    @Test
    public void scanDevices_shouldOpenBluetoothSettings_whenAdapterIsNotEnabled() {
        when(mAdapter.isEnabled()).thenReturn(false);
        mPresenter.scanDevices();
        verify(mView).openBluetoothSettings(REQUEST_ENABLE_BLUETOOTH);
    }

    @Test
    public void scanDevices_shouldStopScanning_whenFinished() {
        when(mAdapter.isEnabled()).thenReturn(true);

        mPresenter.scanDevices();

        verify(mView).showProgressIndicator();
        verify(mExecutor).execute(mRunnable.capture());
        mRunnable.getValue().run();
        verify(mAdapter).startScanning(mScanDeviceCallback.capture());
        mScanDeviceCallback.getValue().onDeviceScanned(DEVICE);
        verify(mView).showDevices(DEVICE_LIST);
        verify(mHandler).postDelayed(mRunnable.capture(), anyLong());
        mRunnable.getValue().run();
        verify(mView).hideProgressIndicator();
        verify(mAdapter).stopScanning(mScanDeviceCallback.getValue());
    }

    @Test
    public void scanDevices_shouldReturn_whenScanning() {
        when(mAdapter.isEnabled()).thenReturn(true);

        mPresenter.scanDevices();
        // 2nd scan
        mPresenter.scanDevices();
        verify(mView, times(1)).showProgressIndicator();
    }
}
