package com.george.medicmetrics.bluetooth.service;

import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;

import java.util.List;
import java.util.UUID;

public class FakeBluetoothGattService implements GattService {

    private String mUuid;
    private List<GattCharacteristic> mGattCharacteristicList;

    public FakeBluetoothGattService(String uuid, List<GattCharacteristic> gattCharacteristicList) {
        mUuid = uuid;
        mGattCharacteristicList = gattCharacteristicList;
    }

    @NonNull
    @Override
    public UUID getUuid() {
        return UUID.fromString(mUuid);
    }

    @NonNull
    @Override
    public List<GattCharacteristic> getCharacteristics() {
        return mGattCharacteristicList;
    }
}
