package com.george.medicmetrics.ui.connect;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;

import java.util.List;

public interface DeviceConnection {

    void connect(@NonNull String address);

    void disconnect();

    @Nullable
    List<GattService> getGattServices();

    boolean readGattCharacteristic(@NonNull GattCharacteristic characteristic);

    boolean notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled);
}
