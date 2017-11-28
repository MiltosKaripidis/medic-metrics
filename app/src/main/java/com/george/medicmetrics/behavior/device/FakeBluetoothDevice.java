package com.george.medicmetrics.behavior.device;

import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.gatt.FakeBluetoothGatt;
import com.george.medicmetrics.behavior.gatt.Gatt;

public class FakeBluetoothDevice implements Device {

    private String mName;
    private String mAddress;

    public FakeBluetoothDevice(@NonNull String name, @NonNull String address) {
        mName = name;
        mAddress = address;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getAddress() {
        return mAddress;
    }

    @Nullable
    public Gatt connectGatt(@NonNull Context context, boolean autoConnect, @NonNull final ConnectGattCallback callback) {
        final Gatt gatt = new FakeBluetoothGatt();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onConnectionStateChange(gatt, 0, BluetoothProfile.STATE_CONNECTED);
            }
        }, 2000);

        return gatt;
    }
}
