package com.george.medicmetrics.bluetooth.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.device.Device;

public interface Adapter {

    boolean isEnabled();

    void startScanning(@Nullable ScanDeviceCallback callback);

    void stopScanning(@Nullable ScanDeviceCallback callback);

    @Nullable
    Device getDevice(@Nullable String address);

    interface ScanDeviceCallback {

        void onDeviceScanned(@NonNull Device device);
    }
}
