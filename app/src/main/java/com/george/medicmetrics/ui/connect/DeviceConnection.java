package com.george.medicmetrics.ui.connect;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.service.GattService;

import java.util.List;

/**
 * Defines a bluetooth device GATT connectivity and handling behavior.
 */
public interface DeviceConnection {

    /**
     * Connects to a bluetooth device's GATT for the given MAC address. An intent broadcast will be
     * sent with the {@link ConnectDeviceService#ACTION_GATT_CONNECTED} action.
     *
     * @param address A String representing the MAC address that will try to connect to.
     */
    void connect(@NonNull String address);

    /**
     * Disconnects from an already connected bluetooth device. An intent broadcast will be
     * sent with the {@link ConnectDeviceService#ACTION_GATT_DISCONNECTED} action.
     */
    void disconnect();

    /**
     * Gets the bluetooth GATT service list for the given bluetooth device. An intent broadcast will
     * be sent with the {@link ConnectDeviceService#ACTION_GATT_SERVICES_DISCOVERED} action.
     *
     * @return A List of {@link GattService}s for the given bluetooth device.
     */
    @Nullable
    List<GattService> getGattServices();

    /**
     * Tries to read a specific bluetooth GATT characteristic.
     *
     * @param characteristic The {@link GattCharacteristic}'s data that will be read.
     */
    void readGattCharacteristic(@NonNull GattCharacteristic characteristic);

    /**
     * This method will notify the client periodically for the specific characteristic's data. An
     * intent broadcast will be sent with the {@link ConnectDeviceService#ACTION_DATA_AVAILABLE}
     * action for each notification.
     *
     * @param characteristic The {@link GattCharacteristic}'s data that will be read.
     * @param enabled        A boolean indicating whether the notification operation should start.
     */
    void notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled);
}
