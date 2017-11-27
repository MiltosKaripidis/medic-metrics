package com.george.medicmetrics.data;

import android.bluetooth.BluetoothDevice;

public class Device {

    private String mName;
    private String mAddress;

    public Device() {
        // Default constructor.
    }

    public Device(BluetoothDevice bluetoothDevice) {
        mName = bluetoothDevice.getName();
        mAddress = bluetoothDevice.getAddress();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
