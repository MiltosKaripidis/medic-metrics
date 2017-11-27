package com.george.medicmetrics.ui.scan;

import android.support.annotation.NonNull;

import com.george.medicmetrics.data.Device;
import com.george.medicmetrics.ui.base.BaseContract;

import java.util.List;

interface ScanDeviceContract {

    interface View extends BaseContract.View {

        void showProgressIndicator();

        void hideProgressIndicator();

        void openBluetoothSettings(int requestCode);

        void finish();

        void showDevices(@NonNull List<Device> deviceList);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void handleBluetoothSettingsResult(int requestCode, int resultCode);

        void scanDevices();

        void stopScanning();
    }
}
