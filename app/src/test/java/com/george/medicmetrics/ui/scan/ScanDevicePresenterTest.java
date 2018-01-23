package com.george.medicmetrics.ui.scan;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;

import com.george.medicmetrics.bluetooth.adapter.Adapter;
import com.george.medicmetrics.bluetooth.device.Device;
import com.george.medicmetrics.bluetooth.device.FakeBluetoothDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.Executor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScanDevicePresenterTest {

    private static final int INVALID_REQUEST_CODE = -1;
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final int[] PERMISSION_DENIED = new int[]{PackageManager.PERMISSION_DENIED};
    private static final int[] PERMISSION_GRANTED = new int[]{PackageManager.PERMISSION_GRANTED};
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String EIMO_ADDRESS = "OBP";

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
    private ArgumentCaptor<Runnable> mRunnableCaptor;

    @Captor
    private ArgumentCaptor<Adapter.ScanDeviceCallback> mScanDeviceCallbackCaptor;

    @Before
    public void setupPresenter() {
        mPresenter = new ScanDevicePresenter(mHandler, mAdapter, mExecutor);
        mPresenter.attachView(mView);
    }

    @Test
    public void tryToScanDevices_scansDevices_whenNotNeedsRuntimePermission() {
        mPresenter.tryToScanDevices();

        verify(mAdapter).isEnabled();
    }

    @Test
    public void tryToScanDevices_requestsFineLocationPermission_whenNotHasFineLocationPermission() {
        when(mView.needsRuntimePermission()).thenReturn(true);

        mPresenter.tryToScanDevices();

        verify(mView).requestFineLocationPermission();
        verify(mAdapter, never()).isEnabled();
    }

    @Test
    public void tryToScanDevices_scansDevices_whenHasFineLocationPermission() {
        when(mView.needsRuntimePermission()).thenReturn(true);
        when(mView.hasFineLocationPermission()).thenReturn(true);

        mPresenter.tryToScanDevices();

        verify(mAdapter).isEnabled();
    }

    @Test
    public void handleSettingsResult_finishes_whenInvalidRequestCode() {
        mPresenter.handleSettingsResult(INVALID_REQUEST_CODE, Activity.RESULT_OK);

        verify(mView).finish();
    }

    @Test
    public void handleSettingsResult_finishes_whenInvalidResultCode() {
        mPresenter.handleSettingsResult(REQUEST_ENABLE_BLUETOOTH, Activity.RESULT_CANCELED);

        verify(mView).finish();
    }

    @Test
    public void handleSettingsResult_notFinishes_whenValidRequestCodeAndResultCode() {
        mPresenter.handleSettingsResult(REQUEST_ENABLE_BLUETOOTH, Activity.RESULT_OK);

        verify(mView, never()).finish();
    }

    @Test
    public void handleAccessFineLocationPermissionResult_finishes_whenPermissionDenied() {
        mPresenter.handleAccessFineLocationPermissionResult(PERMISSION_DENIED);

        verify(mView).finish();
    }

    @Test
    public void handleAccessFineLocationPermissionResult_notFinishes_whenPermissionGranted() {
        mPresenter.handleAccessFineLocationPermissionResult(PERMISSION_GRANTED);

        verify(mView, never()).finish();
    }

    @Test
    public void scanDevices_opensBluetoothSettings_whenAdapterIsDisabled() {
        mPresenter.scanDevices();

        verify(mView).openBluetoothSettings(anyInt());
        verify(mView, never()).isGpsEnabled();
    }

    @Test
    public void scanDevices_showsLocationEnableDialog_whenGpsIsDisabled() {
        when(mAdapter.isEnabled()).thenReturn(true);

        mPresenter.scanDevices();

        verify(mView, never()).openBluetoothSettings(anyInt());
        verify(mView).showLocationEnableDialog();
        verify(mView, never()).showProgressIndicator();
    }

    @Test
    public void scanDevices_returns_whenScanning() {
        when(mAdapter.isEnabled()).thenReturn(true);
        when(mView.isGpsEnabled()).thenReturn(true);

        mPresenter.scanDevices();
        // 2nd scan
        mPresenter.scanDevices();
        verify(mView, times(1)).showProgressIndicator();
    }

    @Test
    public void scanDevices_notShowsDevices_whenNotEimoDevice() {
        when(mAdapter.isEnabled()).thenReturn(true);
        when(mView.isGpsEnabled()).thenReturn(true);

        mPresenter.scanDevices();

        verify(mView, never()).openBluetoothSettings(anyInt());
        verify(mView, never()).showLocationEnableDialog();
        verify(mView).hideEmptyDevices();
        verify(mView).showProgressIndicator();
        verify(mExecutor).execute(mRunnableCaptor.capture());
        mRunnableCaptor.getValue().run();
        verify(mAdapter).startScanning(mScanDeviceCallbackCaptor.capture());
        mScanDeviceCallbackCaptor.getValue().onDeviceScanned(createDevice());
        verify(mView, never()).showDevices(any(List.class));
    }

    @Test
    public void scanDevices_notShowsDevices_whenDuplicateDevices() {
        when(mAdapter.isEnabled()).thenReturn(true);
        when(mView.isGpsEnabled()).thenReturn(true);

        mPresenter.scanDevices();

        verify(mView, never()).openBluetoothSettings(anyInt());
        verify(mView, never()).showLocationEnableDialog();
        verify(mView).hideEmptyDevices();
        verify(mView).showProgressIndicator();
        verify(mExecutor).execute(mRunnableCaptor.capture());
        mRunnableCaptor.getValue().run();
        verify(mAdapter).startScanning(mScanDeviceCallbackCaptor.capture());
        mScanDeviceCallbackCaptor.getValue().onDeviceScanned(createEimoDevice());
        // 2nd device scan
        verify(mAdapter).startScanning(mScanDeviceCallbackCaptor.capture());
        mScanDeviceCallbackCaptor.getValue().onDeviceScanned(createEimoDevice());

        verify(mView, times(1)).showDevices(any(List.class));
    }

    @Test
    public void scanDevices_showsDevices() {
        when(mAdapter.isEnabled()).thenReturn(true);
        when(mView.isGpsEnabled()).thenReturn(true);

        mPresenter.scanDevices();

        verify(mView, never()).openBluetoothSettings(anyInt());
        verify(mView, never()).showLocationEnableDialog();
        verify(mView).hideEmptyDevices();
        verify(mView).showProgressIndicator();
        verify(mExecutor).execute(mRunnableCaptor.capture());
        mRunnableCaptor.getValue().run();
        verify(mAdapter).startScanning(mScanDeviceCallbackCaptor.capture());
        mScanDeviceCallbackCaptor.getValue().onDeviceScanned(createEimoDevice());

        verify(mView).showDevices(any(List.class));
    }

    @Test
    public void stopsScanning_showsEmptyDevices_whenNoDevices() {
        when(mAdapter.isEnabled()).thenReturn(true);
        when(mView.isGpsEnabled()).thenReturn(true);

        mPresenter.scanDevices();

        verify(mView, never()).openBluetoothSettings(anyInt());
        verify(mView, never()).showLocationEnableDialog();
        verify(mView).showProgressIndicator();
        verify(mExecutor).execute(mRunnableCaptor.capture());
        mRunnableCaptor.getValue().run();
        verify(mAdapter).startScanning(mScanDeviceCallbackCaptor.capture());
        mScanDeviceCallbackCaptor.getValue().onDeviceScanned(createEimoDevice());

        verify(mView).showDevices(any(List.class));

        verify(mHandler).postDelayed(mRunnableCaptor.capture(), anyLong());
        mRunnableCaptor.getValue().run();
        verify(mView).hideProgressIndicator();
        verify(mView, times(2)).hideEmptyDevices();
        verify(mAdapter).stopScanning(mScanDeviceCallbackCaptor.capture());
    }

    @Test
    public void stopsScanning_hidesEmptyDevices_whenExistingDevices() {
        when(mAdapter.isEnabled()).thenReturn(true);
        when(mView.isGpsEnabled()).thenReturn(true);

        mPresenter.scanDevices();

        verify(mView).hideEmptyDevices();
        verify(mView, never()).openBluetoothSettings(anyInt());
        verify(mView, never()).showLocationEnableDialog();
        verify(mView).showProgressIndicator();

        verify(mHandler).postDelayed(mRunnableCaptor.capture(), anyLong());
        mRunnableCaptor.getValue().run();
        verify(mView).hideProgressIndicator();
        verify(mView).showEmptyDevices();
        verify(mAdapter).stopScanning(mScanDeviceCallbackCaptor.capture());
    }

    @Test
    public void detachView_stopsScanning() {
        mPresenter.detachView();

        verify(mHandler).removeCallbacks(mRunnableCaptor.capture());
    }

    private Device createDevice() {
        return new FakeBluetoothDevice(NAME, ADDRESS, null);
    }

    private Device createEimoDevice() {
        return new FakeBluetoothDevice(NAME, EIMO_ADDRESS, null);
    }
}
