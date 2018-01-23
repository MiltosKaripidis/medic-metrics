package com.george.medicmetrics.bluetooth.gatt;

import android.bluetooth.BluetoothGatt;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.descriptor.Descriptor;
import com.george.medicmetrics.bluetooth.service.GattService;

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
    public void readCharacteristic(@NonNull GattCharacteristic gattCharacteristic) {
        mCallback.onCharacteristicRead(FakeBluetoothGatt.this, gattCharacteristic, BluetoothGatt.GATT_SUCCESS);
    }

    @Override
    public void notifyCharacteristic(@NonNull final GattCharacteristic gattCharacteristic, boolean enabled) {
        if (!enabled) {
            mScheduledExecutorService.shutdown();
            return;
        }

        if (mScheduledExecutorService.isShutdown()) {
            mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mCallback.onCharacteristicChanged(FakeBluetoothGatt.this, gattCharacteristic);
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void writeDescriptor(@NonNull Descriptor descriptor) {
        // TODO: Implement
    }

    @Override
    public void close() {
        mScheduledExecutorService.shutdown();
    }
}
