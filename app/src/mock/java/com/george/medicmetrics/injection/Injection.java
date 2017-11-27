package com.george.medicmetrics.injection;

import android.content.Context;

import com.george.medicmetrics.behavior.bluetooth.BluetoothScanBehavior;
import com.george.medicmetrics.behavior.bluetooth.FakeBluetoothAdapter;

public class Injection {

    public static BluetoothScanBehavior provideBluetoothScanBehavior(Context context) {
        return new FakeBluetoothAdapter();
    }
}
