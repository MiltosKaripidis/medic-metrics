package com.george.medicmetrics.ui.connect;

import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.bluetooth.Adapter;
import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.behavior.gatt.ConnectGattCallback;
import com.george.medicmetrics.behavior.gatt.Gatt;
import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;
import com.george.medicmetrics.injection.Injection;

import java.util.List;

public class BluetoothService extends Service {

    public final static String ACTION_GATT_CONNECTED = "com.george.medicmetrics.data.GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.george.medicmetrics.data.GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.george.medicmetrics.data.GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.george.medicmetrics.data.DATA_AVAILABLE";
    public final static String EXTRA_DATA = "com.george.medicmetrics.data.DATA";
    public final static String EXTRA_UUID = "com.george.medicmetrics.data.UUID";
    public static final String UUID_HEART_RATE = "00002a37-0000-1000-8000-00805f9b34fb";
    public static final String UUID_BODY_TEMPERATURE = "00002902-0000-1000-8000-00805f9b34fb";
    private final IBinder mIBinder = new LocalBinder();
    private Adapter mAdapter;
    private Gatt mGatt;

    public class LocalBinder extends Binder {
        public BluetoothService getService() {
            return BluetoothService.this;
        }
    }

    private ConnectGattCallback mConnectGattCallback = new ConnectGattCallback() {
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
            if (status != BluetoothGatt.GATT_SUCCESS) return;
            broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
        }

        @Override
        public void onCharacteristicRead(@NonNull Gatt gatt, @NonNull GattCharacteristic characteristic, int status) {
            if (status != BluetoothGatt.GATT_SUCCESS) return;
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }

        @Override
        public void onCharacteristicChanged(@NonNull Gatt gatt, @NonNull GattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    private void broadcastUpdate(@NonNull String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(@NonNull String action, @NonNull GattCharacteristic characteristic) {
        // TODO: Implement
        String data;
        String uuid = characteristic.getUuid().toString();
        switch (uuid) {
            case UUID_HEART_RATE:
                data = getHeartRate(characteristic);
                break;
            case UUID_BODY_TEMPERATURE:
                data = getBodyTemperature(characteristic);
                break;
            default:
                data = null;
                break;
        }

        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_UUID, uuid);
        intent.putExtra(EXTRA_DATA, data);
        sendBroadcast(intent);
    }

    // TODO: Implement
    private String getHeartRate(@NonNull GattCharacteristic characteristic) {
        Integer heartRateInt = characteristic.getIntValue(0, 0);
        return String.valueOf(heartRateInt);
    }

    // TODO: Implement
    private String getBodyTemperature(@NonNull GattCharacteristic characteristic) {
        Integer heartRateInt = characteristic.getIntValue(0, 0);
        return String.valueOf(heartRateInt);
    }

    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public void close() {
        if (mGatt == null) {
            return;
        }

        mGatt.close();
        mGatt = null;
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

    @Nullable
    public List<GattService> getGattServices() {
        return mGatt == null ? null : mGatt.getServices();
    }

    public boolean readGattCharacteristic(GattCharacteristic gattCharacteristic) {
        return !(mAdapter == null || mGatt == null) && mGatt.readCharacteristic(gattCharacteristic);
    }

    public boolean notifyGattCharacteristic(GattCharacteristic gattCharacteristic, boolean enabled) {
        return !(mAdapter == null || mGatt == null) && mGatt.notifyCharacteristic(gattCharacteristic, enabled);
    }
}
