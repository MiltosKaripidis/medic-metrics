package com.george.medicmetrics.bluetooth.gatt;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.characteristic.RealBluetoothGattCharacteristic;
import com.george.medicmetrics.bluetooth.descriptor.Descriptor;
import com.george.medicmetrics.bluetooth.descriptor.RealBluetoothDescriptor;
import com.george.medicmetrics.bluetooth.service.GattService;
import com.george.medicmetrics.bluetooth.service.RealBluetoothGattService;

import java.util.ArrayList;
import java.util.List;

public class RealBluetoothGatt implements Gatt {

    private BluetoothGatt mBluetoothGatt;

    public RealBluetoothGatt(BluetoothGatt bluetoothGatt) {
        mBluetoothGatt = bluetoothGatt;
    }

    @Override
    public boolean discoverServices() {
        return mBluetoothGatt.discoverServices();
    }

    @NonNull
    @Override
    public List<GattService> getServices() {
        List<BluetoothGattService> bluetoothGattServiceList = mBluetoothGatt.getServices();
        List<GattService> gattServiceList = new ArrayList<>();
        for (BluetoothGattService bluetoothGattService : bluetoothGattServiceList) {
            GattService gattService = new RealBluetoothGattService(bluetoothGattService);
            gattServiceList.add(gattService);
        }
        return gattServiceList;
    }

    @Override
    public void readCharacteristic(@NonNull GattCharacteristic gattCharacteristic) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = ((RealBluetoothGattCharacteristic) gattCharacteristic).getBluetoothGattCharacteristic();
        mBluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
    }

    @Override
    public void notifyCharacteristic(@NonNull GattCharacteristic gattCharacteristic, boolean enabled) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = ((RealBluetoothGattCharacteristic) gattCharacteristic).getBluetoothGattCharacteristic();
        mBluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, enabled);
    }

    @Override
    public void writeDescriptor(@NonNull Descriptor descriptor) {
        BluetoothGattDescriptor bluetoothGattDescriptor = ((RealBluetoothDescriptor) descriptor).getBluetoothGattDescriptor();
        mBluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
    }

    @Override
    public void close() {
        mBluetoothGatt.close();
    }
}
