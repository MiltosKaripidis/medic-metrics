package com.george.medicmetrics.ui.metrics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.service.GattService;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.List;

class MetricsPresenter extends BasePresenter<MetricsContract.View> implements MetricsContract.Presenter {

    @Override
    public void handleGattServices(@Nullable List<GattService> gattServiceList) {
        GattCharacteristic heartRateCharacteristic = getCharacteristic(gattServiceList, GattCharacteristic.UUID_HEART_RATE);
        GattCharacteristic bodyTemperatureCharacteristic = getCharacteristic(gattServiceList, GattCharacteristic.UUID_BODY_TEMPERATURE);

        notifyCharacteristic(heartRateCharacteristic);
        notifyCharacteristic(bodyTemperatureCharacteristic);
    }

    @Override
    public void handleData(@NonNull String uuid, @NonNull String data) {
        switch (uuid) {
            case GattCharacteristic.UUID_HEART_RATE:
                mView.showHeartRate(data);
                break;
            case GattCharacteristic.UUID_BODY_TEMPERATURE:
                mView.showBodyTemperature(data);
                break;
        }
    }

    @Nullable
    private GattCharacteristic getCharacteristic(@Nullable List<GattService> gattServiceList, @NonNull String uuid) {
        if (gattServiceList == null) return null;

        for (GattService gattService : gattServiceList) {
            for (GattCharacteristic characteristic : gattService.getCharacteristics()) {
                String currentUuid = characteristic.getUuid().toString();
                if (uuid.equals(currentUuid)) {
                    return characteristic;
                }
            }
        }
        return null;
    }

    private void readCharacteristic(@Nullable GattCharacteristic characteristic) {
        if (characteristic == null) return;

        if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            mView.readGattCharacteristic(characteristic);
        }
    }

    private void notifyCharacteristic(@Nullable GattCharacteristic characteristic) {
        if (characteristic == null) return;

        if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            mView.notifyGattCharacteristic(characteristic, true);
        }
    }
}
