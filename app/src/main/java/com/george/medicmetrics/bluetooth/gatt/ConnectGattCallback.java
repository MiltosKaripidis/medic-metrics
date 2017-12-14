package com.george.medicmetrics.bluetooth.gatt;

import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;

public interface ConnectGattCallback {

        void onConnectionStateChange(@NonNull Gatt gatt, int status, int newState);

        void onServicesDiscovered(@NonNull Gatt gatt, int status);

        void onCharacteristicRead(@NonNull Gatt gatt, @NonNull GattCharacteristic characteristic, int status);

        void onCharacteristicChanged(@NonNull Gatt gatt, @NonNull GattCharacteristic characteristic);
    }