package com.george.medicmetrics.behavior.gatt;

import android.bluetooth.BluetoothGatt;

public class RealBluetoothGatt implements Gatt {

    private BluetoothGatt mBluetoothGatt;

    public RealBluetoothGatt(BluetoothGatt bluetoothGatt) {
        mBluetoothGatt = bluetoothGatt;
    }

    @Override
    public boolean discoverServices() {
        return mBluetoothGatt.discoverServices();
    }
}
