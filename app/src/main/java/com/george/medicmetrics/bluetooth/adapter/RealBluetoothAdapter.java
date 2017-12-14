package com.george.medicmetrics.bluetooth.adapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.device.Device;
import com.george.medicmetrics.bluetooth.device.RealBluetoothDevice;

public class RealBluetoothAdapter implements Adapter {

    private BluetoothAdapter mBluetoothAdapter;
    private ScanDeviceCallback mScanDeviceCallback;

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(@NonNull BluetoothDevice device, int rssi, byte[] scanRecord) {
            Device scannedDevice = new RealBluetoothDevice(device);
            mScanDeviceCallback.onDeviceScanned(scannedDevice);
        }
    };

    public RealBluetoothAdapter(@Nullable BluetoothAdapter bluetoothAdapter) {
        if (bluetoothAdapter == null) {
            throw new IllegalArgumentException("BluetoothAdapter == null");
        }
        mBluetoothAdapter = bluetoothAdapter;
    }

    @Override
    public boolean isEnabled() {
        return mBluetoothAdapter.isEnabled();
    }

    @Override
    public void startScanning(@Nullable final ScanDeviceCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ScanDeviceCallback == null");
        }

        mScanDeviceCallback = callback;
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    @Override
    public void stopScanning(@Nullable final ScanDeviceCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ScanDeviceCallback == null");
        }

        mScanDeviceCallback = callback;
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    @Nullable
    @Override
    public Device getDevice(@Nullable String address) {
        BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
        return new RealBluetoothDevice(bluetoothDevice);
    }
}
