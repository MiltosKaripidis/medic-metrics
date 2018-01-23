package com.george.medicmetrics.bluetooth.device;

import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.gatt.ConnectGattCallback;
import com.george.medicmetrics.bluetooth.gatt.FakeBluetoothGatt;
import com.george.medicmetrics.bluetooth.gatt.Gatt;

public class FakeBluetoothDevice implements Device {

    private String mName;
    private String mAddress;
    private Gatt mGatt;

    public FakeBluetoothDevice(String name, String address, Gatt gatt) {
        mName = name;
        mAddress = address;
        mGatt = gatt;
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
        ((FakeBluetoothGatt) mGatt).setConnectGattCallback(callback);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onConnectionStateChange(mGatt, 0, BluetoothProfile.STATE_CONNECTED);
            }
        }, 1000);

        return mGatt;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FakeBluetoothDevice)) {
            return false;
        }

        FakeBluetoothDevice device = (FakeBluetoothDevice) obj;
        return mAddress.equals(device.getAddress());
    }
}
