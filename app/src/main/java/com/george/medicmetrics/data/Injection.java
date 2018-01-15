package com.george.medicmetrics.data;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.adapter.Adapter;
import com.george.medicmetrics.bluetooth.adapter.RealBluetoothAdapter;

public class Injection {

    @NonNull
    public static DataSource provideDataSource(Context context) {
        return Repository.getInstance(new LocalRepository(context), new PreferencesRepository(context));
    }

    @NonNull
    public static Adapter provideAdapter(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return new RealBluetoothAdapter(bluetoothAdapter);
    }
}
