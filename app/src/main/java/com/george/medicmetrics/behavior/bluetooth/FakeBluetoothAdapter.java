package com.george.medicmetrics.behavior.bluetooth;

import android.os.Handler;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.device.Device;

import java.util.List;

public class FakeBluetoothAdapter implements Adapter {

    private static final long SAMSUNG_SCAN_DELAY = 2000;
    private static final long EIMO_SCAN_DELAY = 4000;
    private Handler mHandler = new Handler();
    private List<Device> mDeviceList;

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

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onDeviceScanned(mDeviceList.get(0));
            }
        }, SAMSUNG_SCAN_DELAY);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onDeviceScanned(mDeviceList.get(1));
            }
        }, EIMO_SCAN_DELAY);
    }

    @Override
    public void stopScanning(@Nullable ScanDeviceCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ScanDeviceCallback == null");
        }
    }

    @Nullable
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
