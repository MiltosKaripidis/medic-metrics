package com.george.medicmetrics.ui.scan;

import android.support.annotation.NonNull;

import com.george.medicmetrics.data.Device;

import java.util.List;

interface ScanDeviceContract {

    interface View {

        void showProgressIndicator();

        void hideProgressIndicator();

        void openBluetoothSettings(int requestCode);

        void finish();

        void showDevices(@NonNull List<Device> deviceList);
    }

    interface Presenter {

        void attachView(@NonNull View view);

        void detachView();

        void handleBluetoothSettingsResult(int requestCode, int resultCode);

        void scanDevices();

        void stopScanning();
    }
}
