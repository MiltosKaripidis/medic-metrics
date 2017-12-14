package com.george.medicmetrics.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.adapter.Adapter;
import com.george.medicmetrics.bluetooth.adapter.FakeBluetoothAdapter;
import com.george.medicmetrics.bluetooth.characteristic.FakeBluetoothGattCharacteristic;
import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.descriptor.Descriptor;
import com.george.medicmetrics.bluetooth.descriptor.FakeBluetoothDescriptor;
import com.george.medicmetrics.bluetooth.device.Device;
import com.george.medicmetrics.bluetooth.device.FakeBluetoothDevice;
import com.george.medicmetrics.bluetooth.gatt.FakeBluetoothGatt;
import com.george.medicmetrics.bluetooth.gatt.Gatt;
import com.george.medicmetrics.bluetooth.service.FakeBluetoothGattService;
import com.george.medicmetrics.bluetooth.service.GattService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Injection {

    @NonNull
    public static Adapter provideAdapter(@NonNull Context context) {
        return new FakeBluetoothAdapter(getDeviceList());
    }

    private static List<Device> getDeviceList() {
        List<Device> deviceList = new ArrayList<>();
        Device samsungGearIconx = new FakeBluetoothDevice("Samsung Gear Iconx", "68:76:4f:21:88:5a", getGatt());
        Device eimo = new FakeBluetoothDevice("Eimo", "5a:88:21:4f:76:68", getGatt());
        deviceList.add(samsungGearIconx);
        deviceList.add(eimo);
        return deviceList;
    }

    private static Gatt getGatt() {
        return new FakeBluetoothGatt(getServices());
    }

    private static List<GattService> getServices() {
        List<GattService> gattServiceList = new ArrayList<>();
        GattService gattService = new FakeBluetoothGattService("00002a37-0000-1000-8000-00805f9b34fb", getCharacteristics());
        gattServiceList.add(gattService);
        return gattServiceList;
    }

    private static List<GattCharacteristic> getCharacteristics() {
        List<GattCharacteristic> gattCharacteristicList = new ArrayList<>();
        GattCharacteristic heartRateCharacteristic = new FakeBluetoothGattCharacteristic(GattCharacteristic.UUID_HEART_RATE, getHeartRates(), getDescriptors());
        GattCharacteristic bodyTemperatureCharacteristic = new FakeBluetoothGattCharacteristic(GattCharacteristic.UUID_BODY_TEMPERATURE, getBodyTemperatures(), getDescriptors());
        gattCharacteristicList.add(heartRateCharacteristic);
        gattCharacteristicList.add(bodyTemperatureCharacteristic);
        return gattCharacteristicList;
    }

    private static List<Descriptor> getDescriptors() {
        List<Descriptor> descriptorList = new ArrayList<>();
        Descriptor descriptor = new FakeBluetoothDescriptor(UUID.fromString(GattCharacteristic.UUID_CONFIG_CHARACTERISTIC));
        descriptorList.add(descriptor);
        return descriptorList;
    }

    private static List<Integer> getHeartRates() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(65);
        integerList.add(63);
        integerList.add(80);
        integerList.add(77);
        integerList.add(101);
        return integerList;
    }

    private static List<Integer> getBodyTemperatures() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(36);
        integerList.add(38);
        integerList.add(34);
        integerList.add(37);
        integerList.add(40);
        return integerList;
    }
}
