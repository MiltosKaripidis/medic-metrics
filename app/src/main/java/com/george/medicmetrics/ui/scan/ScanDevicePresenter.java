package com.george.medicmetrics.ui.scan;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.bluetooth.BluetoothScanBehavior;
import com.george.medicmetrics.data.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

class ScanDevicePresenter implements ScanDeviceContract.Presenter {

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final long SCAN_PERIOD = 10000;
    private ScanDeviceContract.View mView;
    private BluetoothScanBehavior mBluetoothScanBehavior;
    private Handler mHandler;
    private boolean mScanning;
    private List<Device> mDeviceList;
    private Executor mExecutor;

    private BluetoothScanBehavior.ScanDeviceCallback mScanDeviceCallback = new BluetoothScanBehavior.ScanDeviceCallback() {
        @Override
        public void onDeviceScanned(Device device) {
            Device scannedDevice = new Device();
            scannedDevice.setName(device.getName());
            scannedDevice.setAddress(device.getAddress());
            mDeviceList.add(scannedDevice);
            mView.showDevices(mDeviceList);
        }
    };

    ScanDevicePresenter(@NonNull Handler handler,
                        @NonNull BluetoothScanBehavior bluetoothScanBehavior,
                        @NonNull Executor executor) {
        mHandler = handler;
        mBluetoothScanBehavior = bluetoothScanBehavior;
        mExecutor = executor;
        mDeviceList = new ArrayList<>();
    }

    @Override
    public void attachView(@NonNull ScanDeviceContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void handleBluetoothSettingsResult(int requestCode, int resultCode) {
        if (requestCode == REQUEST_ENABLE_BLUETOOTH && resultCode == Activity.RESULT_OK) {
            return;
        }

        mView.finish();
    }

    @Override
    public void scanDevices() {
        if (!mBluetoothScanBehavior.isEnabled()) {
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
                mBluetoothScanBehavior.startScanning(mScanDeviceCallback);
            }
        });
    }

    @Override
    public void stopScanning() {
        mView.hideProgressIndicator();
        mScanning = false;
        mBluetoothScanBehavior.stopScanning(mScanDeviceCallback);
    }
}
