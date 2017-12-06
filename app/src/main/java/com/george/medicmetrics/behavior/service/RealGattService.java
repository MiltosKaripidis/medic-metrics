package com.george.medicmetrics.behavior.service;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.characteristic.RealGattCharacteristic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RealGattService implements GattService {

    private BluetoothGattService mBluetoothGattService;

    public RealGattService(BluetoothGattService bluetoothGattService) {
        mBluetoothGattService = bluetoothGattService;
    }

    @NonNull
    @Override
    public UUID getUuid() {
        return mBluetoothGattService.getUuid();
    }

    @NonNull
    @Override
    public List<GattCharacteristic> getCharacteristics() {
        List<BluetoothGattCharacteristic> bluetoothGattCharacteristicList = mBluetoothGattService.getCharacteristics();
        List<GattCharacteristic> gattCharacteristicList = new ArrayList<>();
        for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattCharacteristicList) {
            GattCharacteristic gattCharacteristic = new RealGattCharacteristic(bluetoothGattCharacteristic);
            gattCharacteristicList.add(gattCharacteristic);
        }
        return gattCharacteristicList;
    }
}
