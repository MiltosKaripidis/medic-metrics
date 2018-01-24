package com.george.medicmetrics.bluetooth.adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.device.Device;

import java.util.List;

public class FakeBluetoothAdapter implements Adapter {

    private static final long SAMSUNG_SCAN_DELAY = 2000;
    private static final long EIMO_SCAN_DELAY = 4000;
    private Handler mHandler = new Handler();
    private List<Device> mDeviceList;
    private ScanDeviceCallback mCallback;
    private Runnable mSamsungRunnable = new Runnable() {
        @Override
        public void run() {
            mCallback.onDeviceScanned(mDeviceList.get(0));
        }
    };
    private Runnable mEimoRunnable = new Runnable() {
        @Override
        public void run() {
            mCallback.onDeviceScanned(mDeviceList.get(1));
        }
    };

    public FakeBluetoothAdapter(List<Device> deviceList) {
        mDeviceList = deviceList;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void startScanning(@Nullable final ScanDeviceCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ScanDeviceCallback == null");
        }

        mCallback = callback;
        mHandler.postDelayed(mSamsungRunnable, SAMSUNG_SCAN_DELAY);
        mHandler.postDelayed(mEimoRunnable, EIMO_SCAN_DELAY);
    }

    @Override
    public void stopScanning(@Nullable ScanDeviceCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ScanDeviceCallback == null");
        }

        mHandler.removeCallbacks(mSamsungRunnable);
        mHandler.removeCallbacks(mEimoRunnable);
    }

    @NonNull
    @Override
    public Device getDevice(@Nullable String address) {
        if (address == null) return null;

        switch (address) {
            case "68:76:4f:21:88:5a":
                return mDeviceList.get(0);
            case "5a:88:21:4f:76:68":
                return mDeviceList.get(1);
            default:
                return null;
        }
    }
}
