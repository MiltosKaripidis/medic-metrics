package com.george.medicmetrics.ui.connect;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.adapter.Adapter;
import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.behavior.gatt.ConnectGattCallback;
import com.george.medicmetrics.behavior.gatt.Gatt;
import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.descriptor.Descriptor;
import com.george.medicmetrics.behavior.gatt.service.GattService;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.List;
import java.util.UUID;

class ConnectDevicePresenter extends BasePresenter<ConnectDeviceContract.View> implements ConnectDeviceContract.Presenter {

    private Adapter mAdapter;
    private Gatt mGatt;

    private ConnectGattCallback mConnectGattCallback = new ConnectGattCallback() {
        @Override
        public void onConnectionStateChange(@NonNull Gatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mView.broadcastAction(ConnectDeviceService.ACTION_GATT_CONNECTED);
                mGatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mView.broadcastAction(ConnectDeviceService.ACTION_GATT_DISCONNECTED);
            }
        }

        @Override
        public void onServicesDiscovered(@NonNull Gatt gatt, int status) {
            if (status != BluetoothGatt.GATT_SUCCESS) return;
            mView.broadcastAction(ConnectDeviceService.ACTION_GATT_SERVICES_DISCOVERED);
        }

        @Override
        public void onCharacteristicRead(@NonNull Gatt gatt, @NonNull GattCharacteristic characteristic, int status) {
            if (status != BluetoothGatt.GATT_SUCCESS) return;
            String uuid = characteristic.getUuid().toString();
            String data = getData(uuid, characteristic);
            if (data == null) return;
            mView.broadcastAction(ConnectDeviceService.ACTION_DATA_AVAILABLE, uuid, data);
        }

        @Override
        public void onCharacteristicChanged(@NonNull Gatt gatt, @NonNull GattCharacteristic characteristic) {
            String uuid = characteristic.getUuid().toString();
            String data = getData(uuid, characteristic);
            if (data == null) return;
            mView.broadcastAction(ConnectDeviceService.ACTION_DATA_AVAILABLE, uuid, data);
        }
    };

    ConnectDevicePresenter(@NonNull Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void connect(@NonNull String deviceAddress) {
        Device device = mAdapter.getDevice(deviceAddress);
        if (device == null) return;

        mGatt = mView.getDeviceGatt(device, false, mConnectGattCallback);
    }

    @Override
    public void disconnect() {
        if (mGatt == null) {
            return;
        }

        mGatt.close();
        mGatt = null;
    }

    @Nullable
    @Override
    public List<GattService> getGattServices() {
        return mGatt == null ? null : mGatt.getServices();
    }

    @Override
    public void readGattCharacteristic(@NonNull GattCharacteristic characteristic) {
        if (mAdapter == null || mGatt == null) return;
        mGatt.readCharacteristic(characteristic);
    }

    @Override
    public void notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled) {
        if ((mAdapter == null || mGatt == null)) return;

        mGatt.notifyCharacteristic(characteristic, enabled);

        String uuid = characteristic.getUuid().toString();
        if (uuid.equals(GattCharacteristic.UUID_HEART_RATE)) {
            Descriptor descriptor = characteristic.getDescriptor(UUID.fromString(GattCharacteristic.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mGatt.writeDescriptor(descriptor);
        }
    }

    @Nullable
    private String getData(@NonNull String uuid, @NonNull GattCharacteristic characteristic) {
        switch (uuid) {
            case GattCharacteristic.UUID_HEART_RATE:
                return getHeartRate(characteristic);
            case GattCharacteristic.UUID_BODY_TEMPERATURE:
                return getBodyTemperature(characteristic);
            default:
                return null;
        }
    }

    @NonNull
    private String getHeartRate(@NonNull GattCharacteristic characteristic) {
        int flag = characteristic.getProperties();
        int format;
        if ((flag & 0x01) != 0) {
            format = BluetoothGattCharacteristic.FORMAT_UINT16;
        } else {
            format = BluetoothGattCharacteristic.FORMAT_UINT8;
        }
        Integer heartRate = characteristic.getIntValue(format, 1);
        return String.valueOf(heartRate);
    }

    // TODO: Implement
    @NonNull
    private String getBodyTemperature(@NonNull GattCharacteristic characteristic) {
        Integer temperature = characteristic.getIntValue(0, 0);
        return String.valueOf(temperature);
    }
}
