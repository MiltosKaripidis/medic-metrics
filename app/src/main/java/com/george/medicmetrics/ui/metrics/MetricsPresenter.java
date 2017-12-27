package com.george.medicmetrics.ui.metrics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.service.GattService;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.List;
import java.util.Locale;

class MetricsPresenter extends BasePresenter<MetricsContract.View> implements MetricsContract.Presenter {

    @Override
    public void handleDeviceConnected() {
        mView.showDeviceConnected();
    }

    @Override
    public void handleDeviceDisconnected() {
        mView.showDeviceDisconnected();
    }

    @Override
    public void handleGattServices(@Nullable List<GattService> gattServiceList) {
        GattCharacteristic heartRateCharacteristic = getCharacteristic(gattServiceList, GattCharacteristic.UUID_HEART_RATE);
        GattCharacteristic bodyTemperatureCharacteristic = getCharacteristic(gattServiceList, GattCharacteristic.UUID_BODY_TEMPERATURE);

        notifyCharacteristic(heartRateCharacteristic);
        notifyCharacteristic(bodyTemperatureCharacteristic);
    }

    @Override
    public void handleData(@NonNull String uuid, @NonNull Record record) {
        if (uuid.equals(GattCharacteristic.UUID_BODY_TEMPERATURE)) {
//            String bloodOxygen = String.format(Locale.getDefault(), "%.0f", record.getBloodOxygen());
//            mView.showBloodOxygen(bloodOxygen);
//            String systolicBloodPressure = String.format(Locale.getDefault(), "%.0f", record.getSystolicBloodPressure());
//            mView.showSystolicBloodPressure(systolicBloodPressure);
            String heartRate = String.format(Locale.getDefault(), "%.0f", record.getHearRate());
            mView.showHeartRate(heartRate);
            String bodyTemperature = String.format(Locale.getDefault(), "%.1f", record.getBodyTemperature());
            mView.showBodyTemperature(bodyTemperature);
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
