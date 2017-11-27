package com.george.medicmetrics.injection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import com.george.medicmetrics.behavior.bluetooth.BluetoothScanBehavior;
import com.george.medicmetrics.behavior.bluetooth.RealBluetoothAdapter;

public class Injection {

    public static BluetoothScanBehavior provideBluetoothScanBehavior(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return new RealBluetoothAdapter(bluetoothAdapter);
    }
}
