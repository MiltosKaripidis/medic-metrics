package com.george.medicmetrics.bluetooth.descriptor;

import android.bluetooth.BluetoothGattDescriptor;
import android.support.annotation.NonNull;

import java.util.UUID;

public class RealBluetoothDescriptor implements Descriptor {

    private BluetoothGattDescriptor mBluetoothGattDescriptor;

    public RealBluetoothDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        mBluetoothGattDescriptor = bluetoothGattDescriptor;
    }

    @NonNull
    @Override
    public UUID getUuid() {
        return mBluetoothGattDescriptor.getUuid();
    }

    @Override
    public void setValue(@NonNull byte[] value) {
        mBluetoothGattDescriptor.setValue(value);
    }

    public BluetoothGattDescriptor getBluetoothGattDescriptor() {
        return mBluetoothGattDescriptor;
    }
}
