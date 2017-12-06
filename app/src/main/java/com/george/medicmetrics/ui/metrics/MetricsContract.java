package com.george.medicmetrics.ui.metrics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.service.GattService;
import com.george.medicmetrics.ui.base.BaseContract;

import java.util.List;

interface MetricsContract {

    interface View extends BaseContract.View {

        void showDeviceConnected();

        void readGattCharacteristic(@NonNull GattCharacteristic characteristic);

        void notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled);

        void showHeartRate(@NonNull String bpm);

        void showBodyTemperature(@NonNull String temperature);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void handleGattServices(@Nullable List<GattService> gattServiceList);

        void handleData(@NonNull String uuid, @NonNull String data);
    }
}
