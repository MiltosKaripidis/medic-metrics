package com.george.medicmetrics.ui.connect;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.behavior.gatt.Gatt;
import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;
import com.george.medicmetrics.ui.base.BaseContract;

import java.util.List;

interface ConnectDeviceContract {

    interface View extends BaseContract.View {

        @Nullable
        Gatt getDeviceGatt(@NonNull Device device, boolean autoConnect);

        void broadcastAction(@NonNull String action);

        void broadcastAction(@NonNull String action, @NonNull String uuid, @NonNull String data);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void connect(@NonNull String address);

        void onConnectionStateChange(@NonNull Gatt gatt, int newState);

        void onServicesDiscovered(int status);

        void onCharacteristicRead(@NonNull GattCharacteristic characteristic, int status);

        void onCharacteristicChanged(@NonNull GattCharacteristic characteristic);

        @Nullable
        List<GattService> getGattServices();

        boolean readGattCharacteristic(@NonNull GattCharacteristic characteristic);

        boolean notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled);
    }
}
