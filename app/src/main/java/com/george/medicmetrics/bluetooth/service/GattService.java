package com.george.medicmetrics.bluetooth.service;

import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;

import java.util.List;
import java.util.UUID;

public interface GattService {

    @NonNull
    UUID getUuid();

    @NonNull
    List<GattCharacteristic> getCharacteristics();
}
