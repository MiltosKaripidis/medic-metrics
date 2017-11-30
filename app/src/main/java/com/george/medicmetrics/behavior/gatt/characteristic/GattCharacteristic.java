package com.george.medicmetrics.behavior.gatt.characteristic;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

public interface GattCharacteristic {

    @NonNull
    UUID getUuid();

    int getProperties();

    @Nullable
    Integer getIntValue(int formatType, int offset);
}
