package com.george.medicmetrics.behavior.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.george.medicmetrics.data.Device;

public class RealBluetoothAdapter implements BluetoothScanBehavior {

    private BluetoothAdapter mBluetoothAdapter;
    private ScanDeviceCallback mScanDeviceCallback;

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Device scannedDevice = new Device(device);
            mScanDeviceCallback.onDeviceScanned(scannedDevice);
        }
    };

    public RealBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        mBluetoothAdapter = bluetoothAdapter;
    }

    @Override
    public boolean isEnabled() {
        return mBluetoothAdapter.isEnabled();
    }

    @Override
    public void startScanning(final ScanDeviceCallback callback) {
        mScanDeviceCallback = callback;
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    @Override
    public void stopScanning(final ScanDeviceCallback callback) {
        mScanDeviceCallback = callback;
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }
}
