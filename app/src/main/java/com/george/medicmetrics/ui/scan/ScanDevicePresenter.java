package com.george.medicmetrics.ui.scan;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.bluetooth.Adapter;
import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

class ScanDevicePresenter extends BasePresenter<ScanDeviceContract.View> implements ScanDeviceContract.Presenter {

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final long SCAN_PERIOD = 10000;
    private Adapter mAdapter;
    private Handler mHandler;
    private boolean mScanning;
    private List<Device> mDeviceList;
    private Executor mExecutor;

    private Adapter.ScanDeviceCallback mScanDeviceCallback = new Adapter.ScanDeviceCallback() {
        @Override
        public void onDeviceScanned(@NonNull Device device) {
            mDeviceList.add(device);
            mView.showDevices(mDeviceList);
        }
    };

    ScanDevicePresenter(@NonNull Handler handler,
                        @NonNull Adapter adapter,
                        @NonNull Executor executor) {
        mHandler = handler;
        mAdapter = adapter;
        mExecutor = executor;
        mDeviceList = new ArrayList<>();
    }

    @Override
    public void handleBluetoothSettingsResult(int requestCode, int resultCode) {
        if (requestCode == REQUEST_ENABLE_BLUETOOTH && resultCode == Activity.RESULT_OK) {
            return;
        }

        mView.finish();
    }

    @Override
    public void tryToGetUserLocation() {
        if (!mView.needsRuntimePermission()) {
            return;
        }

        if (!mView.hasFineLocationPermission()) {
            mView.requestFineLocationPermission();
            return;
        }

        mView.scanDevices();
    }

    @Override
    public void handleAccessFineLocationPermissionResult(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mView.scanDevices();
        } else {
            mView.showGpsError();
        }
    }

    @Override
    public void scanDevices() {
        if (!mAdapter.isEnabled()) {
            mView.openBluetoothSettings(REQUEST_ENABLE_BLUETOOTH);
            return;
        }

        if (mScanning) {
            return;
        }

        mView.showProgressIndicator();
        mDeviceList.clear();
        mScanning = true;

        // Stops scanning after the given period.
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopScanning();
            }
        }, SCAN_PERIOD);

        // Executes on a background thread.
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mAdapter.startScanning(mScanDeviceCallback);
            }
        });
    }

    @Override
    public void stopScanning() {
        mView.hideProgressIndicator();
        mScanning = false;
        mAdapter.stopScanning(mScanDeviceCallback);
    }
}
