package com.george.medicmetrics.ui.metrics;

import android.os.Handler;

import com.george.medicmetrics.bluetooth.characteristic.FakeBluetoothGattCharacteristic;
import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.service.FakeBluetoothGattService;
import com.george.medicmetrics.bluetooth.service.GattService;
import com.george.medicmetrics.objects.Record;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MetricsPresenterTest {

    private static final String SERVICE_UUID = "00002a37-0000-1000-8000-00805f9b34fb";
    private static final String WRONG_UUID = "00002a37-0000-1000-8000-00805f9b34fb";
    private static final double BLOOD_OXYGEN = 1;
    private static final String EXPECTED_BLOOD_OXYGEN = "1";
    private static final double BODY_TEMPERATURE = 1;
    private static final String EXPECTED_BODY_TEMPERATURE = "1.0";
    private static final double SYSTOLIC_BLOOD_PRESSURE = 1;
    private static final String EXPECTED_SYSTOLIC_BLOOD_PRESSURE = "1";
    private static final double HEART_RATE = 1;
    private static final String EXPECTED_HEART_RATE = "1";

    private MetricsPresenter mPresenter;

    @Mock
    private MetricsContract.View mView;

    @Mock
    private Handler mHandler;

    @Captor
    private ArgumentCaptor<Runnable> mRunnableArgumentCaptor;

    @Before
    public void setupPresenter() {
        mPresenter = new MetricsPresenter(mHandler);
        mPresenter.attachView(mView);
    }

    @Test
    public void handleGattServices_returns_whenGattServicesIsNull() {
        mPresenter.notifyMetrics(null);

        verify(mView, never()).notifyGattCharacteristic(any(GattCharacteristic.class), anyBoolean());
    }

    @Test
    public void handleGattServices_returns_whenGattCharacteristicNotMatches() {
        List<GattService> gattServiceList = getWrongServices();

        mPresenter.notifyMetrics(gattServiceList);

        verify(mView, never()).notifyGattCharacteristic(any(GattCharacteristic.class), anyBoolean());
    }

    @Test
    public void handleGattServices_notifiesGattCharacteristic_whenGattCharacteristicMatches() {
        List<GattService> gattServiceList = getServices();
        GattCharacteristic gattCharacteristic = gattServiceList.get(0).getCharacteristics().get(0);

        mPresenter.notifyMetrics(gattServiceList);

        verify(mView).notifyGattCharacteristic(gattCharacteristic, true);
    }

    @Test
    public void handleData_returns_whenWrongUuid() {
        mPresenter.handleData(WRONG_UUID, new Record());
        verify(mView, never()).showBloodOxygen(anyString());
    }

    @Test
    public void handleData_showsMetrics_whenUuidIsCorrect() {
        Record record = createRecord();

        mPresenter.handleData(GattCharacteristic.UUID_METRICS, record);

        verify(mHandler).postDelayed(mRunnableArgumentCaptor.capture(), anyLong());
        mRunnableArgumentCaptor.getValue().run();
        verify(mView).openParameters(record);
        verify(mView).showBloodOxygen(EXPECTED_BLOOD_OXYGEN);
        verify(mView).showBodyTemperature(EXPECTED_BODY_TEMPERATURE);
        verify(mView).showSystolicBloodPressure(EXPECTED_SYSTOLIC_BLOOD_PRESSURE);
        verify(mView).showHeartRate(EXPECTED_HEART_RATE);
    }

    private static List<GattService> getWrongServices() {
        List<GattService> gattServiceList = new ArrayList<>();
        GattService gattService = new FakeBluetoothGattService(SERVICE_UUID, getWrongCharacteristics());
        gattServiceList.add(gattService);
        return gattServiceList;
    }

    private static List<GattCharacteristic> getWrongCharacteristics() {
        List<GattCharacteristic> gattCharacteristicList = new ArrayList<>();
        GattCharacteristic characteristic = new FakeBluetoothGattCharacteristic(WRONG_UUID, null, null);
        gattCharacteristicList.add(characteristic);
        return gattCharacteristicList;
    }

    private static List<GattService> getServices() {
        List<GattService> gattServiceList = new ArrayList<>();
        GattService gattService = new FakeBluetoothGattService(SERVICE_UUID, getCharacteristics());
        gattServiceList.add(gattService);
        return gattServiceList;
    }

    private static List<GattCharacteristic> getCharacteristics() {
        List<GattCharacteristic> gattCharacteristicList = new ArrayList<>();
        GattCharacteristic characteristic = new FakeBluetoothGattCharacteristic(GattCharacteristic.UUID_METRICS, null, null);
        gattCharacteristicList.add(characteristic);
        return gattCharacteristicList;
    }

    private Record createRecord() {
        Record record = new Record();
        record.setBloodOxygen(BLOOD_OXYGEN);
        record.setBodyTemperature(BODY_TEMPERATURE);
        record.setSystolicBloodPressure(SYSTOLIC_BLOOD_PRESSURE);
        record.setHearRate(HEART_RATE);
        return record;
    }
}
