package com.george.medicmetrics.bluetooth.characteristic;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.descriptor.Descriptor;
import com.george.medicmetrics.bluetooth.descriptor.RealBluetoothDescriptor;

import java.util.UUID;

public class RealBluetoothGattCharacteristic implements GattCharacteristic {

    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;

    public RealBluetoothGattCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
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

    @Nullable
    @Override
    public byte[] getValue() {
        return mBluetoothGattCharacteristic.getValue();
    }

    @Nullable
    @Override
    public Descriptor getDescriptor(@NonNull UUID uuid) {
        BluetoothGattDescriptor bluetoothGattDescriptor = mBluetoothGattCharacteristic.getDescriptor(uuid);
        return new RealBluetoothDescriptor(bluetoothGattDescriptor);
    }

    public BluetoothGattCharacteristic getBluetoothGattCharacteristic() {
        return mBluetoothGattCharacteristic;
    }
}
