package com.george.medicmetrics.ui.connect;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.behavior.gatt.ConnectGattCallback;
import com.george.medicmetrics.behavior.gatt.Gatt;
import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;
import com.george.medicmetrics.ui.base.BaseContract;

import java.util.List;

interface ConnectDeviceContract {

    interface View extends BaseContract.View {

        @Nullable
        Gatt getDeviceGatt(@NonNull Device device, boolean autoConnect, @NonNull ConnectGattCallback callback);

        void broadcastAction(@NonNull String action);

        void broadcastAction(@NonNull String action, @NonNull String uuid, @NonNull String data);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void connect(@NonNull String address);

        void disconnect();

        @Nullable
        List<GattService> getGattServices();

        void readGattCharacteristic(@NonNull GattCharacteristic characteristic);

        void notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled);
    }
}
