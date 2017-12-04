package com.george.medicmetrics.behavior.gatt;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.characteristic.RealGattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;
import com.george.medicmetrics.behavior.gatt.service.RealGattService;

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
            GattService gattService = new RealGattService(bluetoothGattService);
            gattServiceList.add(gattService);
        }
        return gattServiceList;
    }

    @Override
    public void readCharacteristic(@NonNull GattCharacteristic gattCharacteristic) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = ((RealGattCharacteristic) gattCharacteristic).getBluetoothGattCharacteristic();
        mBluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
    }

    @Override
    public void notifyCharacteristic(@NonNull GattCharacteristic gattCharacteristic, boolean enabled) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = ((RealGattCharacteristic) gattCharacteristic).getBluetoothGattCharacteristic();
        mBluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, enabled);
    }

    @Override
    public void close() {
        mBluetoothGatt.close();
    }
}
