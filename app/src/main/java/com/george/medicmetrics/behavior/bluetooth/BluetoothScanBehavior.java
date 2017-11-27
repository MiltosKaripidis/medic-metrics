package com.george.medicmetrics.behavior.bluetooth;

import com.george.medicmetrics.data.Device;

public interface BluetoothScanBehavior {

    boolean isEnabled();

    void startScanning(ScanDeviceCallback callback);

    void stopScanning(ScanDeviceCallback callback);

    interface ScanDeviceCallback {

        void onDeviceScanned(Device device);
    }
}
