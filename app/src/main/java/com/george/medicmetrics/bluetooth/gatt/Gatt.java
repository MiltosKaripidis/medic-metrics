package com.george.medicmetrics.bluetooth.gatt;

import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.descriptor.Descriptor;
import com.george.medicmetrics.bluetooth.service.GattService;

import java.util.List;

public interface Gatt {

    boolean discoverServices();

    @NonNull
    List<GattService> getServices();

    void readCharacteristic(@NonNull GattCharacteristic gattCharacteristic);

    void notifyCharacteristic(@NonNull GattCharacteristic gattCharacteristic, boolean enabled);

    void writeDescriptor(@NonNull Descriptor descriptor);

    void close();
}
