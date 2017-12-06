package com.george.medicmetrics.behavior.service;

import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.characteristic.GattCharacteristic;

import java.util.List;
import java.util.UUID;

public interface GattService {

    @NonNull
    UUID getUuid();

    @NonNull
    List<GattCharacteristic> getCharacteristics();
}
