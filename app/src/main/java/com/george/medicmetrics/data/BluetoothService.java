package com.george.medicmetrics.data;

import android.app.Service;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.bluetooth.Adapter;
import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.behavior.gatt.Gatt;
import com.george.medicmetrics.injection.Injection;

public class BluetoothService extends Service {

    public final static String ACTION_GATT_CONNECTED = "com.george.medicmetrics.data.GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.george.medicmetrics.data.GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.george.medicmetrics.data.GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.george.medicmetrics.data.DATA_AVAILABLE";
    public final static String EXTRA_DATA = "com.george.medicmetrics.data.DATA";
    private final IBinder mIBinder = new LocalBinder();
    private Adapter mAdapter;
    private Gatt mGatt;

    public class LocalBinder extends Binder {
        public BluetoothService getService() {
            return BluetoothService.this;
        }
    }

    private Device.ConnectGattCallback mConnectGattCallback = new Device.ConnectGattCallback() {
        @Override
        public void onConnectionStateChange(@NonNull Gatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                broadcastUpdate(intentAction);
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(@NonNull Gatt gatt, int status) {
            // TODO: Implement
        }

        @Override
        public void onCharacteristicRead(@NonNull Gatt gatt, @NonNull BluetoothGattCharacteristic characteristic, int status) {
            // TODO: Implement
        }

        @Override
        public void onCharacteristicChanged(@NonNull Gatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {
            // TODO: Implement
        }
    };

    private void broadcastUpdate(@NonNull String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return mIBinder;
    }

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, BluetoothService.class);
    }

    public void initialize() {
        mAdapter = Injection.provideAdapter(this);
    }

    public boolean connect(@NonNull String deviceAddress) {
        Device device = mAdapter.getDevice(deviceAddress);
        if (device == null) return false;

        mGatt = device.connectGatt(this, false, mConnectGattCallback);
        return true;
    }
}
