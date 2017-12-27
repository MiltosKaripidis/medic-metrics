package com.george.medicmetrics.ui.metrics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.service.GattService;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BaseContract;

import java.util.List;

interface MetricsContract {

    interface View extends BaseContract.View {

        void showDeviceConnected();

        void showDeviceDisconnected();

        void notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled);

        void showHeartRate(@NonNull String bpm);

        void showBodyTemperature(@NonNull String temperature);

        void showBloodOxygen(@NonNull String percent);

        void showSystolicBloodPressure(@NonNull String bloodPressure);

        void updateProgressBar(int percent);

        void openMoreMetrics(@NonNull Record record);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void handleDeviceConnected();

        void handleDeviceDisconnected();

        void handleGattServices(@Nullable List<GattService> gattServiceList);

        void handleData(@NonNull String uuid, @NonNull Record record);
    }
}
