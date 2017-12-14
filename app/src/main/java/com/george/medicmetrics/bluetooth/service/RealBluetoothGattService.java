package com.george.medicmetrics.bluetooth.service;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.characteristic.RealBluetoothGattCharacteristic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RealBluetoothGattService implements GattService {

    private BluetoothGattService mBluetoothGattService;

    public RealBluetoothGattService(BluetoothGattService bluetoothGattService) {
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
            GattCharacteristic gattCharacteristic = new RealBluetoothGattCharacteristic(bluetoothGattCharacteristic);
            gattCharacteristicList.add(gattCharacteristic);
        }
        return gattCharacteristicList;
    }
}
