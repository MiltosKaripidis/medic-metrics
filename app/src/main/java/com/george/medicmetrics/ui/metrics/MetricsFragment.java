package com.george.medicmetrics.ui.metrics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.george.medicmetrics.R;
import com.george.medicmetrics.behavior.gatt.characteristic.GattCharacteristic;
import com.george.medicmetrics.behavior.gatt.service.GattService;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.connect.ConnectDeviceService;

import java.util.List;

public class MetricsFragment extends BaseFragment<MetricsContract.Presenter> {

    private static final String ARG_DEVICE_NAME = "device_name";
    private static final String ARG_DEVICE_ADDRESS = "device_address";
    private ConnectDeviceService mConnectDeviceService;
    private boolean mBound;
    private String mDeviceName;
    private String mDeviceAddress;
    private TextView mHeartRateTextView;
    private TextView mBodyTemperatureTextView;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ConnectDeviceService.LocalBinder localBinder = (ConnectDeviceService.LocalBinder) service;
            mConnectDeviceService = localBinder.getService();
            mConnectDeviceService.initialize();
            mConnectDeviceService.connect(mDeviceAddress);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    private BroadcastReceiver mGattReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ConnectDeviceService.ACTION_GATT_CONNECTED:
                    Toast.makeText(context, "Connected!", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectDeviceService.ACTION_GATT_DISCONNECTED:
                    // TODO: Implement
                    break;
                case ConnectDeviceService.ACTION_GATT_SERVICES_DISCOVERED:
                    // TODO: Implement
                    GattCharacteristic heartRateCharacteristic = getCharacteristic(mConnectDeviceService.getGattServices(), ConnectDeviceService.UUID_HEART_RATE);
                    GattCharacteristic bodyTemperatureCharacteristic = getCharacteristic(mConnectDeviceService.getGattServices(), ConnectDeviceService.UUID_BODY_TEMPERATURE);

                    handleCharacteristic(heartRateCharacteristic);
                    handleCharacteristic(bodyTemperatureCharacteristic);
                    break;
                case ConnectDeviceService.ACTION_DATA_AVAILABLE:
                    // TODO: Implement
                    String uuid = intent.getStringExtra(ConnectDeviceService.EXTRA_UUID);
                    String data = intent.getStringExtra(ConnectDeviceService.EXTRA_DATA);
                    showData(uuid, data);
                    break;
                default:
                    break;
            }
        }
    };

    private void showData(@NonNull String uuid, @NonNull String data) {
        switch (uuid) {
            case ConnectDeviceService.UUID_HEART_RATE:
                String heartRate = getString(R.string.format_heart_rate, data);
                mHeartRateTextView.setText(heartRate);
                break;
            case ConnectDeviceService.UUID_BODY_TEMPERATURE:
                String bodyTemperature = getString(R.string.format_body_temperature, data);
                mBodyTemperatureTextView.setText(bodyTemperature);
                break;
        }
    }

    @Nullable
    private GattCharacteristic getCharacteristic(@Nullable List<GattService> gattServiceList, @NonNull String uuid) {
        if (gattServiceList == null) return null;

        for (GattService gattService : gattServiceList) {
            for (GattCharacteristic characteristic : gattService.getCharacteristics()) {
                String currentUuid = characteristic.getUuid().toString();
                if (uuid.equals(currentUuid)) {
                    return characteristic;
                }
            }
        }
        return null;
    }

    private void handleCharacteristic(@Nullable GattCharacteristic characteristic) {
        if (characteristic == null) return;

        if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            mConnectDeviceService.readGattCharacteristic(characteristic);
        } else if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            mConnectDeviceService.notifyGattCharacteristic(characteristic, true);
        }
    }

    @NonNull
    @Override
    protected MetricsContract.Presenter createPresenter() {
        return new MetricsPresenter();
    }

    public static MetricsFragment newInstance(@NonNull String deviceName,
                                              @NonNull String deviceAddress) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DEVICE_NAME, deviceName);
        bundle.putString(ARG_DEVICE_ADDRESS, deviceAddress);

        MetricsFragment fragment = new MetricsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeviceName = getArguments().getString(ARG_DEVICE_NAME);
        mDeviceAddress = getArguments().getString(ARG_DEVICE_ADDRESS);

        Intent intent = ConnectDeviceService.newIntent(getContext());
        getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connect_device, container, false);

        mHeartRateTextView = view.findViewById(R.id.heart_rate_text_view);
        mBodyTemperatureTextView = view.findViewById(R.id.body_temperature_text_view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mGattReceiver, createGattIntentFilter());
        if (mConnectDeviceService != null) {
            mConnectDeviceService.connect(mDeviceAddress);
        }
    }

    private IntentFilter createGattIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectDeviceService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(ConnectDeviceService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(ConnectDeviceService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(ConnectDeviceService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(mGattReceiver);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        getActivity().unbindService(mServiceConnection);
        mConnectDeviceService = null;
        super.onDestroy();
    }
}
