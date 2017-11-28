package com.george.medicmetrics.behavior.device;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.gatt.Gatt;

public interface Device {

    @NonNull
    String getName();

    @NonNull
    String getAddress();

    @Nullable
    Gatt connectGatt(@NonNull Context context, boolean autoConnect, @NonNull ConnectGattCallback callback);

    interface ConnectGattCallback {

        void onConnectionStateChange(@NonNull Gatt gatt, int status, int newState);

        void onServicesDiscovered(@NonNull Gatt gatt, int status);

        void onCharacteristicRead(@NonNull Gatt gatt, @NonNull BluetoothGattCharacteristic characteristic, int status);

        void onCharacteristicChanged(@NonNull Gatt gatt, @NonNull BluetoothGattCharacteristic characteristic);
    }
}
