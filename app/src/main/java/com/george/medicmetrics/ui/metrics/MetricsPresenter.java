package com.george.medicmetrics.ui.metrics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.service.GattService;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.List;
import java.util.Locale;

class MetricsPresenter extends BasePresenter<MetricsContract.View> implements MetricsContract.Presenter {

    private static final int DELAY_IN_MILLIS = 60000;
    private Handler mHandler;
    private boolean mCounting;
    private int mElapsedSeconds;

    MetricsPresenter(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void handleGattServices(@Nullable List<GattService> gattServiceList) {
        GattCharacteristic characteristic = getCharacteristic(gattServiceList, GattCharacteristic.UUID_METRICS);
        notifyCharacteristic(characteristic);
    }

    @Override
    public void handleData(@NonNull String uuid, @NonNull final Record record) {
        if (!mCounting) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mView.openParameters(record);
                }
            }, DELAY_IN_MILLIS);
        }

        if (uuid.equals(GattCharacteristic.UUID_METRICS)) {
            String bloodOxygen = String.format(Locale.getDefault(), "%.0f", record.getBloodOxygen());
            mView.showBloodOxygen(bloodOxygen);
            String systolicBloodPressure = String.format(Locale.getDefault(), "%.0f", record.getSystolicBloodPressure());
            mView.showSystolicBloodPressure(systolicBloodPressure);
            String heartRate = String.format(Locale.getDefault(), "%.0f", record.getHearRate());
            mView.showHeartRate(heartRate);
            String bodyTemperature = String.format(Locale.getDefault(), "%.1f", record.getBodyTemperature());
            mView.showBodyTemperature(bodyTemperature);

            mCounting = true;
            mView.updateProgressBar(calculatePercent());
        }
    }

    private int calculatePercent() {
        mElapsedSeconds++;
        int totalSeconds = DELAY_IN_MILLIS / 1000;
        float percent = ((float) mElapsedSeconds / totalSeconds) * 100f;
        return Math.round(percent);
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

    private void notifyCharacteristic(@Nullable GattCharacteristic characteristic) {
        if (characteristic == null) return;

        if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            mView.notifyGattCharacteristic(characteristic, true);
        }
    }
}
