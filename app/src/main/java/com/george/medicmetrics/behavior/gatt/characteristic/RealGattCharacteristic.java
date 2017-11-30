package com.george.medicmetrics.behavior.gatt.characteristic;

import android.bluetooth.BluetoothGattCharacteristic;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

public class RealGattCharacteristic implements GattCharacteristic {

    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;

    public RealGattCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        mBluetoothGattCharacteristic = bluetoothGattCharacteristic;
    }

    @NonNull
    @Override
    public UUID getUuid() {
        return mBluetoothGattCharacteristic.getUuid();
    }

    @Override
    public int getProperties() {
        return mBluetoothGattCharacteristic.getProperties();
    }

    @Nullable
    @Override
    public Integer getIntValue(int formatType, int offset) {
        return mBluetoothGattCharacteristic.getIntValue(formatType, offset);
    }

    public BluetoothGattCharacteristic getBluetoothGattCharacteristic() {
        return mBluetoothGattCharacteristic;
    }
}
