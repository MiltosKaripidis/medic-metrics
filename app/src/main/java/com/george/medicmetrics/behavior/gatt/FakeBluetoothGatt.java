package com.george.medicmetrics.behavior.gatt;

import android.bluetooth.BluetoothGatt;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FakeBluetoothGatt implements Gatt {

    private ConnectGattCallback mCallback;
    private List<GattService> mGattServiceList;
    private ScheduledExecutorService mScheduledExecutorService;

    public FakeBluetoothGatt(List<GattService> gattServiceList) {
        mGattServiceList = gattServiceList;
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void setConnectGattCallback(ConnectGattCallback callback) {
        mCallback = callback;
    }

    @Override
    public boolean discoverServices() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCallback.onServicesDiscovered(FakeBluetoothGatt.this, BluetoothGatt.GATT_SUCCESS);
            }
        }, 1000);
        return true;
    }

    @NonNull
    @Override
    public List<GattService> getServices() {
        return mGattServiceList;
    }

    @Override
    public boolean readCharacteristic(@NonNull GattCharacteristic gattCharacteristic) {
        mCallback.onCharacteristicRead(FakeBluetoothGatt.this, gattCharacteristic, BluetoothGatt.GATT_SUCCESS);
        return true;
    }

    @Override
    public boolean notifyCharacteristic(@NonNull final GattCharacteristic gattCharacteristic, boolean enabled) {
        if (!enabled) {
            mScheduledExecutorService.shutdown();
            return true;
        }

        if (mScheduledExecutorService.isShutdown()) {
            mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mCallback.onCharacteristicChanged(FakeBluetoothGatt.this, gattCharacteristic);
            }
        }, 0, 1, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public void close() {
        mScheduledExecutorService.shutdown();
    }
}
