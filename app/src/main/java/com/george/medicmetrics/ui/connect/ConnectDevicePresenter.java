package com.george.medicmetrics.ui.connect;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.bluetooth.adapter.Adapter;
import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.descriptor.Descriptor;
import com.george.medicmetrics.bluetooth.device.Device;
import com.george.medicmetrics.bluetooth.gatt.ConnectGattCallback;
import com.george.medicmetrics.bluetooth.gatt.Gatt;
import com.george.medicmetrics.bluetooth.service.GattService;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.List;
import java.util.UUID;

class ConnectDevicePresenter extends BasePresenter<ConnectDeviceContract.View> implements ConnectDeviceContract.Presenter {

    private Adapter mAdapter;
    private Gatt mGatt;

    private ConnectGattCallback mConnectGattCallback = new ConnectGattCallback() {
        @Override
        public void onConnectionStateChange(@NonNull Gatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mView.broadcastAction(ConnectDeviceService.ACTION_GATT_CONNECTED);
                mGatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mView.broadcastAction(ConnectDeviceService.ACTION_GATT_DISCONNECTED);
            }
        }

        @Override
        public void onServicesDiscovered(@NonNull Gatt gatt, int status) {
            if (status != BluetoothGatt.GATT_SUCCESS) return;
            mView.broadcastAction(ConnectDeviceService.ACTION_GATT_SERVICES_DISCOVERED);
        }

        @Override
        public void onCharacteristicRead(@NonNull Gatt gatt, @NonNull GattCharacteristic characteristic, int status) {
            if (status != BluetoothGatt.GATT_SUCCESS) return;
            String uuid = characteristic.getUuid().toString();
            Record record = getRecord(uuid, characteristic);
            if (record == null) return;
            mView.broadcastAction(ConnectDeviceService.ACTION_DATA_AVAILABLE, uuid, record);
        }

        @Override
        public void onCharacteristicChanged(@NonNull Gatt gatt, @NonNull GattCharacteristic characteristic) {
            String uuid = characteristic.getUuid().toString();
            Record record = getRecord(uuid, characteristic);
            if (record == null) return;
            mView.broadcastAction(ConnectDeviceService.ACTION_DATA_AVAILABLE, uuid, record);
        }
    };

    ConnectDevicePresenter(@NonNull Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void connect(@NonNull String deviceAddress) {
        Device device = mAdapter.getDevice(deviceAddress);
        mGatt = mView.getDeviceGatt(device, false, mConnectGattCallback);
    }

    @Override
    public void disconnect() {
        if (mGatt == null) {
            return;
        }

        mGatt.close();
        mGatt = null;
    }

    @Nullable
    @Override
    public List<GattService> getGattServices() {
        return mGatt == null ? null : mGatt.getServices();
    }

    @Override
    public void readGattCharacteristic(@NonNull GattCharacteristic characteristic) {
        if (mAdapter == null || mGatt == null) return;
        mGatt.readCharacteristic(characteristic);
    }

    @Override
    public void notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled) {
        if ((mAdapter == null || mGatt == null)) return;
        mGatt.notifyCharacteristic(characteristic, enabled);

        String uuid = characteristic.getUuid().toString();
        switch (uuid) {
            case GattCharacteristic.UUID_METRICS:
                Descriptor descriptor = characteristic.getDescriptor(UUID.fromString(GattCharacteristic.UUID_CONFIG_CHARACTERISTIC));
                if (descriptor == null) return;
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);
        }
    }

    @Nullable
    private Record getRecord(@NonNull String uuid, @NonNull GattCharacteristic characteristic) {
        if (uuid.equals(GattCharacteristic.UUID_METRICS)) {
            double heartRate = getHeartRate(characteristic);
            double bloodOxygen = getBloodOxygen(characteristic);
            double bloodPressureSystolic = getBloodPressureSystolic(characteristic, heartRate);
            double bodyTemperature = getBodyTemperature(characteristic);

            Record record = new Record();
            record.setHearRate(heartRate);
            record.setBloodOxygen(bloodOxygen);
            record.setSystolicBloodPressure(bloodPressureSystolic);
            record.setBodyTemperature(bodyTemperature);
            return record;
        }

        return null;
    }

    private double getHeartRate(@NonNull GattCharacteristic characteristic) {
        byte[] data = characteristic.getValue();
        if (data == null || data.length <= 0) return 0;

        double heartRate = ((data[4] & 0xFF) << 8) + (data[5] & 0xFF);
        heartRate = heartRate / 100.0;

        if (heartRate < 160 && heartRate > 30) {
            heartRate = 80 * 0.5 + heartRate * 0.5;
        } else {
            heartRate = 80;
        }

        return heartRate;
    }

    private double getBloodOxygen(@NonNull GattCharacteristic characteristic) {
        byte[] data = characteristic.getValue();
        if (data == null || data.length <= 0) return 0;

        double ratio = (((data[0] & 0xFF) << 8) + (data[1] & 0xFF) & 0xffff) << 5;
        double ratiof = ratio / 256.0 / 256.0;
        double spo22_through_reflect_offset = 0.35;
        double ratio_s = 0.61;

        if (ratiof > 1.5 + spo22_through_reflect_offset || ratiof < 0.4 + spo22_through_reflect_offset) {
            ratiof = ratio_s;
        }

        ratio_s = ratio_s * 0.5 + ratiof * 0.5;
        double ratio_soff = ratio_s - spo22_through_reflect_offset;
        double t_r_spo2 = 118.7146 - 33.3298 * ratio_soff - 1.7899 * ratio_soff * ratio_soff;

        t_r_spo2 = t_r_spo2 - 5;
        int r_spo2;

        if (t_r_spo2 <= 96) {
            t_r_spo2 = t_r_spo2 / 5.;
            r_spo2 = (int) t_r_spo2 * 5;
        } else if (t_r_spo2 < 97) {
            r_spo2 = 97;
        } else {
            r_spo2 = (int) (t_r_spo2);
        }

        if (t_r_spo2 > 99) {
            r_spo2 = 99;
        }
        if (t_r_spo2 < 70) {
            r_spo2 = 70;
        }

        return r_spo2;
    }

    private double getBloodPressure(@NonNull GattCharacteristic characteristic, int bloodPressureType, double heartRate) {
        byte[] data = characteristic.getValue();
        if (data == null || data.length <= 0) return 0;

        double pttValue = ((data[2] & 0xFF) << 8) + (data[3] & 0xFF);
        pttValue = pttValue * 16.0 / 1000.0;

        double bloodPressurePttNow = 0.8 * 200.0 + (1 - 0.8) * pttValue;
        double bloodPressureS = 151;
        double bloodPressureD = 80;
        double hb = 81;
        double a = hb * Math.log(bloodPressureS / bloodPressureD);
        double bloodPressureSystolic = 113;
        double bloodPressureDiastolic = 61;

        double Th1 = 6, Th2 = 6;
        double PTT_m = 290;
        if ((bloodPressurePttNow < PTT_m + 50) && (bloodPressurePttNow > PTT_m - 50)) {
            double y1 = 2 / 0.31 * Math.log(PTT_m / bloodPressurePttNow) + 2 / 3 * bloodPressureD + 1 / 3 * bloodPressureS;
            double y2 = (bloodPressureS - bloodPressureD) * (PTT_m / bloodPressurePttNow) * (PTT_m / bloodPressurePttNow);
            double A12 = Math.exp(-a / heartRate), A22 = 1;
            double C11 = 2 / 3, C12 = 1 / 3, C21 = -1, C22 = 1;
            double L11 = 1, L12 = 0.1, L21 = 0.2, L22 = 0.2;
            for (int i = 0; i < 10; i++) {
                double yhat1 = C11 * bloodPressureDiastolic + C12 * bloodPressureSystolic;
                double yhat2 = C21 * bloodPressureDiastolic + C22 * bloodPressureSystolic;
                double e1 = y1 - yhat1, e2 = y2 - yhat2;

                if (e1 > Th1)
                    e1 = Th1;
                else if (e1 < -Th1)
                    e1 = -Th1;

                if (e2 > Th2)
                    e2 = Th2;
                else if (e2 < -Th2)
                    e2 = -Th2;

                bloodPressureDiastolic = A12 * bloodPressureSystolic + L11 * e1 + L12 * e2;
                bloodPressureSystolic = A22 * bloodPressureSystolic + L21 * e1 + L22 * e2;
            }
        }

        switch (bloodPressureType) {
            case 0:
                return bloodPressureSystolic;
            case 1:
                return bloodPressureDiastolic;
        }

        return 0;
    }

    private double getBloodPressureSystolic(@NonNull GattCharacteristic characteristic, double heartRate) {
        return getBloodPressure(characteristic, 0, heartRate);
    }

    private double getBloodPressureDiastolic(@NonNull GattCharacteristic characteristic, double heartRate) {
        return getBloodPressure(characteristic, 1, heartRate);
    }

    private double getBodyTemperature(@NonNull GattCharacteristic characteristic) {
        byte[] data = characteristic.getValue();
        if (data == null || data.length <= 0) return 0;
        if (data.length != 20) return 0;

        double temp_case_v = ((data[9] & 0xFF) << 8) + (data[10] & 0xFF);
        temp_case_v = (temp_case_v - 7800.0) / 90.0 + 25.0;

        double temp_ir_v = ((data[6] & 0xFF) << 16) + ((data[7] & 0xFF) << 8) + (data[8] & 0xFF);
        double calia = 27.80294;
        double calib = -822.118;

        double tb = calib - temp_case_v * calia;
        double tc = 64500 - temp_case_v * calib - temp_ir_v;

        double td = tb * tb - 4 * calia * tc;
        if (td < 0) return 0;

        double bodyTemperature = (-tb + Math.sqrt(td)) / 2 / calia;
        bodyTemperature = bodyTemperature - 0.5;

        return bodyTemperature;
    }
}
