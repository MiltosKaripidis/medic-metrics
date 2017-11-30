package com.george.medicmetrics.behavior.gatt;

import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;

import java.util.List;

public interface Gatt {

    boolean discoverServices();

    @NonNull
    List<GattService> getServices();

    boolean readCharacteristic(@NonNull GattCharacteristic gattCharacteristic);

    boolean notifyCharacteristic(@NonNull GattCharacteristic gattCharacteristic, boolean enabled);

    void close();
}
