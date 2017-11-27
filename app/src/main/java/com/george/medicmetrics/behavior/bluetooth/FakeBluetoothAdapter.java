package com.george.medicmetrics.behavior.bluetooth;

import android.os.Handler;

import com.george.medicmetrics.data.Device;

public class FakeBluetoothAdapter implements BluetoothScanBehavior {

    private static final long SAMSUNG_SCAN_DELAY = 2000;
    private static final long EIMO_SCAN_DELAY = 4000;
    private Handler mHandler = new Handler();

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void startScanning(final ScanDeviceCallback callback) {
        final Device samsungGearIconx = new Device();
        samsungGearIconx.setName("Samsung Gear Iconx");
        samsungGearIconx.setAddress("68:76:4f:21:88:5a");

        final Device eimo = new Device();
        eimo.setName("Eimo");
        eimo.setAddress("5a:88:21:4f:76:68");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onDeviceScanned(samsungGearIconx);
            }
        }, SAMSUNG_SCAN_DELAY);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onDeviceScanned(eimo);
            }
        }, EIMO_SCAN_DELAY);
    }

    @Override
    public void stopScanning(ScanDeviceCallback callback) {
        // Do nothing.
    }
}
