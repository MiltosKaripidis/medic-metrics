package com.george.medicmetrics.injection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.adapter.Adapter;
import com.george.medicmetrics.behavior.adapter.RealBluetoothAdapter;

public class Injection {

    @NonNull
    public static Adapter provideAdapter(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return new RealBluetoothAdapter(bluetoothAdapter);
    }
}
