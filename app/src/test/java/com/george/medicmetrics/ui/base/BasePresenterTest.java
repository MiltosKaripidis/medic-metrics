package com.george.medicmetrics.ui.base;

import com.george.medicmetrics.bluetooth.device.Device;
import com.george.medicmetrics.bluetooth.device.FakeBluetoothDevice;
import com.george.medicmetrics.bluetooth.gatt.FakeBluetoothGatt;
import com.george.medicmetrics.bluetooth.gatt.Gatt;
import com.george.medicmetrics.bluetooth.characteristic.FakeBluetoothGattCharacteristic;
import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.service.FakeBluetoothGattService;
import com.george.medicmetrics.bluetooth.service.GattService;
import com.george.medicmetrics.ui.connect.ConnectDeviceService;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public abstract class BasePresenterTest {

    @Before
    public abstract void setupPresenter();

    protected static List<Device> getDeviceList() {
        List<Device> deviceList = new ArrayList<>();
        Device samsungGearIconx = new FakeBluetoothDevice("Samsung Gear Iconx", "68:76:4f:21:88:5a", getGatt());
        Device eimo = new FakeBluetoothDevice("Eimo", "5a:88:21:4f:76:68", getGatt());
        deviceList.add(samsungGearIconx);
        deviceList.add(eimo);
        return deviceList;
    }

    protected static Gatt getGatt() {
        return new FakeBluetoothGatt(getServices());
    }

    protected static List<GattService> getServices() {
        List<GattService> gattServiceList = new ArrayList<>();
        GattService gattService = new FakeBluetoothGattService("00002a37-0000-1000-8000-00805f9b34fb", getCharacteristics());
        gattServiceList.add(gattService);
        return gattServiceList;
    }

    protected static List<GattCharacteristic> getCharacteristics() {
        List<GattCharacteristic> gattCharacteristicList = new ArrayList<>();
        GattCharacteristic heartRateCharacteristic = new FakeBluetoothGattCharacteristic(ConnectDeviceService.UUID_HEART_RATE, getHeartRates());
        GattCharacteristic bodyTemperatureCharacteristic = new FakeBluetoothGattCharacteristic(ConnectDeviceService.UUID_BODY_TEMPERATURE, getBodyTemperatures());
        gattCharacteristicList.add(heartRateCharacteristic);
        gattCharacteristicList.add(bodyTemperatureCharacteristic);
        return gattCharacteristicList;
    }

    protected static List<Integer> getHeartRates() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(65);
        integerList.add(63);
        integerList.add(80);
        integerList.add(77);
        integerList.add(101);
        return integerList;
    }

    protected static List<Integer> getBodyTemperatures() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(36);
        integerList.add(38);
        integerList.add(34);
        integerList.add(37);
        integerList.add(40);
        return integerList;
    }
}
