package com.george.medicmetrics.ui.metrics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;
import com.george.medicmetrics.ui.base.BaseContract;

import java.util.List;

interface MetricsContract {

    interface View extends BaseContract.View {

        void showDeviceConnected();

        boolean readGattCharacteristic(@NonNull GattCharacteristic characteristic);

        boolean notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled);

        void showHeartRate(@NonNull String bpm);

        void showBodyTemperature(@NonNull String temperature);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void handleGattServices(@Nullable List<GattService> gattServiceList);

        void handleData(@NonNull String uuid, @NonNull String data);
    }
}
