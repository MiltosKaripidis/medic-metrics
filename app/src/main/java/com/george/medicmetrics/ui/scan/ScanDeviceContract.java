package com.george.medicmetrics.ui.scan;

import android.support.annotation.NonNull;

import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.ui.base.BaseContract;

import java.util.List;

interface ScanDeviceContract {

    interface View extends BaseContract.View {

        void showProgressIndicator();

        void hideProgressIndicator();

        void openBluetoothSettings(int requestCode);

        void finish();

        boolean needsRuntimePermission();

        boolean hasFineLocationPermission();

        void requestFineLocationPermission();

        void showGpsError();

        void scanDevices();

        void showDevices(@NonNull List<Device> deviceList);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void handleBluetoothSettingsResult(int requestCode, int resultCode);

        void tryToGetUserLocation();

        void handleAccessFineLocationPermissionResult(int[] grantResults);

        void scanDevices();

        void stopScanning();
    }
}
