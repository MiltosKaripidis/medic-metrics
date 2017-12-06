package com.george.medicmetrics.behavior.service;

import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.characteristic.GattCharacteristic;

import java.util.List;
import java.util.UUID;

public class FakeGattService implements GattService {

    private String mUuid;
    private List<GattCharacteristic> mGattCharacteristicList;

    public FakeGattService(String uuid, List<GattCharacteristic> gattCharacteristicList) {
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
