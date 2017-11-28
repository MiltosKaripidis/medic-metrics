package com.george.medicmetrics.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.bluetooth.Adapter;
import com.george.medicmetrics.behavior.bluetooth.FakeBluetoothAdapter;

public class Injection {

    @NonNull
    public static Adapter provideAdapter(@NonNull Context context) {
        return new FakeBluetoothAdapter();
    }
}
