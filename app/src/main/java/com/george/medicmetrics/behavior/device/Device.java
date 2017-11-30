package com.george.medicmetrics.behavior.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.gatt.ConnectGattCallback;
import com.george.medicmetrics.behavior.gatt.Gatt;

public interface Device {

    @NonNull
    String getName();

    @NonNull
    String getAddress();

    @Nullable
    Gatt connectGatt(@NonNull Context context, boolean autoConnect, @NonNull ConnectGattCallback callback);
}
