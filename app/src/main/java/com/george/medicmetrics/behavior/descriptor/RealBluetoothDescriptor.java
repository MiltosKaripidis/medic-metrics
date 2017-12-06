package com.george.medicmetrics.behavior.descriptor;

import android.bluetooth.BluetoothGattDescriptor;
import android.support.annotation.NonNull;

public class RealBluetoothDescriptor implements Descriptor {

    private BluetoothGattDescriptor mBluetoothGattDescriptor;

    public RealBluetoothDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        mBluetoothGattDescriptor = bluetoothGattDescriptor;
    }

    @Override
    public void setValue(@NonNull byte[] value) {
        mBluetoothGattDescriptor.setValue(value);
    }

    public BluetoothGattDescriptor getBluetoothGattDescriptor() {
        return mBluetoothGattDescriptor;
    }
}
