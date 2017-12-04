package com.george.medicmetrics.behavior.gatt.characteristic;

import android.bluetooth.BluetoothGattCharacteristic;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.gatt.descriptor.Descriptor;

import java.util.List;
import java.util.UUID;

public class FakeGattCharacteristic implements GattCharacteristic {

    private String mUuid;
    private List<Integer> mIntegerList;
    private int mCurrent;

    public FakeGattCharacteristic(String uuid, List<Integer> integerList) {
        mUuid = uuid;
        mIntegerList = integerList;
    }

    @NonNull
    @Override
    public UUID getUuid() {
        return UUID.fromString(mUuid);
    }

    @Override
    public int getProperties() {
        return BluetoothGattCharacteristic.PROPERTY_NOTIFY;
    }

    @Nullable
    @Override
    public Integer getIntValue(int formatType, int offset) {
        if (mCurrent == mIntegerList.size()) {
            mCurrent = 0;
        }
        return mIntegerList.get(mCurrent++);
    }

    @NonNull
    @Override
    public Descriptor getDescriptor(@NonNull UUID uuid) {
        // TODO: Implement
        return null;
    }
}
