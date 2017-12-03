package com.george.medicmetrics.ui.connect;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.george.medicmetrics.behavior.bluetooth.Adapter;
import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.behavior.gatt.ConnectGattCallback;
import com.george.medicmetrics.behavior.gatt.Gatt;
import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;
import com.george.medicmetrics.injection.Injection;
import com.george.medicmetrics.ui.base.BaseService;

import java.util.List;

public class ConnectDeviceService extends BaseService<ConnectDeviceContract.Presenter> implements ConnectDeviceContract.View {

    public final static String ACTION_GATT_CONNECTED = "com.george.medicmetrics.data.GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.george.medicmetrics.data.GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.george.medicmetrics.data.GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.george.medicmetrics.data.DATA_AVAILABLE";

    public final static String EXTRA_DATA = "com.george.medicmetrics.data.DATA";
    public final static String EXTRA_UUID = "com.george.medicmetrics.data.UUID";

    private final IBinder mIBinder = new LocalBinder();

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, ConnectDeviceService.class);
    }

    @NonNull
    @Override
    protected ConnectDeviceContract.Presenter createPresenter() {
        Adapter adapter = Injection.provideAdapter(this);
        return new ConnectDevicePresenter(adapter);
    }

    public class LocalBinder extends Binder {
        public ConnectDeviceService getService() {
            return ConnectDeviceService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return mIBinder;
    }

    public void connect(@NonNull String deviceAddress) {
        mPresenter.connect(deviceAddress);
    }

    public void disconnect() {
        mPresenter.disconnect();
    }

    @Nullable
    @Override
    public Gatt getDeviceGatt(@NonNull Device device, boolean autoConnect, @NonNull ConnectGattCallback callback) {
        return device.connectGatt(this, autoConnect, callback);
    }

    @Nullable
    public List<GattService> getGattServices() {
        return mPresenter.getGattServices();
    }

    public boolean readGattCharacteristic(@NonNull GattCharacteristic gattCharacteristic) {
        return mPresenter.readGattCharacteristic(gattCharacteristic);
    }

    public boolean notifyGattCharacteristic(@NonNull GattCharacteristic gattCharacteristic, boolean enabled) {
        return mPresenter.notifyGattCharacteristic(gattCharacteristic, enabled);
    }

    @Override
    public void broadcastAction(@NonNull String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    @Override
    public void broadcastAction(@NonNull String action, @NonNull String uuid, @NonNull String data) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_UUID, uuid);
        intent.putExtra(EXTRA_DATA, data);
        sendBroadcast(intent);
    }
}
