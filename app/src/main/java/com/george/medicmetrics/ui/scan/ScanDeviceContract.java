package com.george.medicmetrics.ui.scan;

import android.support.annotation.NonNull;

import com.george.medicmetrics.bluetooth.device.Device;
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

        boolean isGpsEnabled();

        void showLocationEnableDialog();

        void showEmptyDevices();

        void hideEmptyDevices();

        void showDevices(@NonNull List<Device> deviceList);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void handleSettingsResult(int requestCode, int resultCode);

        void tryToScanDevices();

        void handleAccessFineLocationPermissionResult(int[] grantResults);

        void scanDevices();

        void stopScanning();
    }
}
