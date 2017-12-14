package com.george.medicmetrics.bluetooth.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.gatt.ConnectGattCallback;
import com.george.medicmetrics.bluetooth.gatt.Gatt;

public interface Device {

    @NonNull
    String getName();

    @NonNull
    String getAddress();

    @Nullable
    Gatt connectGatt(@NonNull Context context, boolean autoConnect, @NonNull ConnectGattCallback callback);
}
