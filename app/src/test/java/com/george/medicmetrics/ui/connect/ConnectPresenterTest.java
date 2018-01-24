package com.george.medicmetrics.ui.connect;

import android.bluetooth.BluetoothGatt;

import com.george.medicmetrics.bluetooth.adapter.Adapter;
import com.george.medicmetrics.bluetooth.characteristic.FakeBluetoothGattCharacteristic;
import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.bluetooth.device.Device;
import com.george.medicmetrics.bluetooth.device.FakeBluetoothDevice;
import com.george.medicmetrics.bluetooth.gatt.ConnectGattCallback;
import com.george.medicmetrics.bluetooth.gatt.FakeBluetoothGatt;
import com.george.medicmetrics.bluetooth.gatt.Gatt;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectPresenterTest {

    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String WRONG_UUID = "00002a37-0000-1000-8000-00805f9b34fb";

    private ConnectDevicePresenter mPresenter;

    @Mock
    private ConnectDeviceContract.View mView;

    @Mock
    private Adapter mAdapter;

    @Mock
    private Gatt mGatt;

    @Captor
    private ArgumentCaptor<ConnectGattCallback> mConnectGattCallbackCaptor;

    @Before
    public void setupPresenter() {
        mPresenter = new ConnectDevicePresenter(mAdapter);
        mPresenter.attachView(mView);
    }

    @Test
    public void connect_broadcastsDisconnectAction_whenStateIsGattDisconnected() {
        connect();
        mConnectGattCallbackCaptor.getValue().onConnectionStateChange(getGatt(), 0, BluetoothGatt.STATE_DISCONNECTED);
        verify(mView).broadcastAction(ConnectDeviceService.ACTION_GATT_DISCONNECTED);
    }

    @Test
    public void connect_broadcastsConnectAction_whenStateIsGattConnected() {
        connect();
        mConnectGattCallbackCaptor.getValue().onConnectionStateChange(getGatt(), 0, BluetoothGatt.STATE_CONNECTED);
        verify(mView).broadcastAction(ConnectDeviceService.ACTION_GATT_CONNECTED);
        verify(mGatt).discoverServices();
    }

    @Test
    public void onServicesDiscovered_returns_whenStatusIsFailure() {
        connect();
        mConnectGattCallbackCaptor.getValue().onServicesDiscovered(getGatt(), BluetoothGatt.GATT_FAILURE);
        verify(mView, never()).broadcastAction(ConnectDeviceService.ACTION_GATT_SERVICES_DISCOVERED);
    }

    @Test
    public void onServicesDiscovered_broadcastsServicesDiscoveredAction_whenStatusIsSuccess() {
        connect();
        mConnectGattCallbackCaptor.getValue().onServicesDiscovered(getGatt(), BluetoothGatt.GATT_SUCCESS);
        verify(mView).broadcastAction(ConnectDeviceService.ACTION_GATT_SERVICES_DISCOVERED);
    }

    @Test
    public void onCharacteristicRead_returns_whenStatusIsFailure() {
        connect();
        mConnectGattCallbackCaptor.getValue().onCharacteristicRead(getGatt(), getCharacteristics().get(0), BluetoothGatt.GATT_FAILURE);
        verify(mView, never()).broadcastAction(anyString(), anyString(), any(Record.class));
    }

    @Test
    public void onCharacteristicRead_returns_whenCharacteristicUuidIsWrong() {
        connect();
        mConnectGattCallbackCaptor.getValue().onCharacteristicRead(getGatt(), getWrongCharacteristics().get(0), BluetoothGatt.GATT_SUCCESS);
        verify(mView, never()).broadcastAction(anyString(), anyString(), any(Record.class));
    }

    @Test
    public void onCharacteristicRead_broadcastsCharacteristic_whenCharacteristicUuidIsRight() {
        connect();
        mConnectGattCallbackCaptor.getValue().onCharacteristicRead(getGatt(), getCharacteristics().get(0), BluetoothGatt.GATT_SUCCESS);
        verify(mView).broadcastAction(anyString(), anyString(), any(Record.class));
    }

    @Test
    public void onCharacteristicChanged_returns_whenCharacteristicUuidIsWrong() {
        connect();
        mConnectGattCallbackCaptor.getValue().onCharacteristicChanged(getGatt(), getWrongCharacteristics().get(0));
        verify(mView, never()).broadcastAction(anyString(), anyString(), any(Record.class));
    }

    @Test
    public void onCharacteristicChanged_broadcastsCharacteristic_whenCharacteristicUuidIsRight() {
        connect();
        mConnectGattCallbackCaptor.getValue().onCharacteristicChanged(getGatt(), getCharacteristics().get(0));
        verify(mView).broadcastAction(anyString(), anyString(), any(Record.class));
    }

    private void connect() {
        Device device = getDevice();
        when(mAdapter.getDevice(ADDRESS)).thenReturn(device);
        when(mView.getDeviceGatt(any(Device.class), anyBoolean(), any(ConnectGattCallback.class))).thenReturn(mGatt);

        mPresenter.connect(ADDRESS);

        verify(mView).getDeviceGatt(any(Device.class), anyBoolean(), mConnectGattCallbackCaptor.capture());
    }

    private Device getDevice() {
        return new FakeBluetoothDevice(NAME, ADDRESS, getGatt());
    }

    private Gatt getGatt() {
        return new FakeBluetoothGatt(getServices());
    }

    private List<GattService> getServices() {
        List<GattService> gattServiceList = new ArrayList<>();
        GattService gattService = new FakeBluetoothGattService("00002a37-0000-1000-8000-00805f9b34fb", getCharacteristics());
        gattServiceList.add(gattService);
        return gattServiceList;
    }

    private List<GattCharacteristic> getWrongCharacteristics() {
        List<GattCharacteristic> gattCharacteristicList = new ArrayList<>();
        GattCharacteristic bodyTemperatureCharacteristic = new FakeBluetoothGattCharacteristic(WRONG_UUID, null, null);
        gattCharacteristicList.add(bodyTemperatureCharacteristic);
        return gattCharacteristicList;
    }

    private List<GattCharacteristic> getCharacteristics() {
        List<GattCharacteristic> gattCharacteristicList = new ArrayList<>();
        GattCharacteristic bodyTemperatureCharacteristic = new FakeBluetoothGattCharacteristic(GattCharacteristic.UUID_METRICS, null, null);
        gattCharacteristicList.add(bodyTemperatureCharacteristic);
        return gattCharacteristicList;
    }
}
