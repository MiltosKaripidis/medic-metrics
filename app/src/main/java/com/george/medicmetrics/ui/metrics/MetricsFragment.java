package com.george.medicmetrics.ui.metrics;

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
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.medicmetrics.R;
import com.george.medicmetrics.bluetooth.characteristic.GattCharacteristic;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.connect.ConnectDeviceService;
import com.george.medicmetrics.ui.connect.DeviceConnection;

public class MetricsFragment extends BaseFragment<MetricsContract.Presenter> implements MetricsContract.View {

    private static final String ARG_DEVICE_NAME = "device_name";
    private static final String ARG_DEVICE_ADDRESS = "device_address";
    private DeviceConnection mDeviceConnection;
    private boolean mBound;
    private String mDeviceName;
    private String mDeviceAddress;
    private TextView mHeartRateTextView;
    private TextView mBodyTemperatureTextView;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(@NonNull ComponentName name, @NonNull IBinder service) {
            ConnectDeviceService.LocalBinder localBinder = (ConnectDeviceService.LocalBinder) service;
            mDeviceConnection = localBinder.getService();
            mDeviceConnection.connect(mDeviceAddress);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(@NonNull ComponentName name) {
            mBound = false;
        }
    };

    private BroadcastReceiver mGattReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(@NonNull Context context, @NonNull Intent intent) {
            String action = intent.getAction();
            if (action == null) return;
            switch (action) {
                case ConnectDeviceService.ACTION_GATT_CONNECTED:
                    mPresenter.handleDeviceConnected();
                    break;
                case ConnectDeviceService.ACTION_GATT_DISCONNECTED:
                    mPresenter.handleDeviceDisconnected();
                    break;
                case ConnectDeviceService.ACTION_GATT_SERVICES_DISCOVERED:
                    mPresenter.handleGattServices(mDeviceConnection.getGattServices());
                    break;
                case ConnectDeviceService.ACTION_DATA_AVAILABLE:
                    String uuid = intent.getStringExtra(ConnectDeviceService.EXTRA_UUID);
                    String data = intent.getStringExtra(ConnectDeviceService.EXTRA_DATA);
                    mPresenter.handleData(uuid, data);
                    break;
                default:
                    break;
            }
        }
    };

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
        if (mDeviceConnection != null) {
            mDeviceConnection.connect(mDeviceAddress);
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
        if (mDeviceConnection != null) {
            mDeviceConnection.disconnect();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        getActivity().unbindService(mServiceConnection);
        mDeviceConnection = null;
        super.onDestroy();
    }

    @Override
    public void showDeviceConnected() {
        String message = getString(R.string.connected, mDeviceName);
        showSnackbar(message, Snackbar.LENGTH_LONG);
    }

    @Override
    public void showDeviceDisconnected() {
        String message = getString(R.string.disconnected);
        showSnackbar(message, Snackbar.LENGTH_LONG);
    }

    private void showSnackbar(@NonNull String message, int duration) {
        View view = getView();
        if (view == null) return;
        Snackbar.make(view, message, duration)
                .show();
    }

    @Override
    public void readGattCharacteristic(@NonNull GattCharacteristic characteristic) {
        mDeviceConnection.readGattCharacteristic(characteristic);
    }

    @Override
    public void notifyGattCharacteristic(@NonNull GattCharacteristic characteristic, boolean enabled) {
        mDeviceConnection.notifyGattCharacteristic(characteristic, enabled);
    }

    @Override
    public void showHeartRate(@NonNull String bpm) {
        String heartRate = getString(R.string.format_heart_rate, bpm);
        mHeartRateTextView.setText(heartRate);
    }

    @Override
    public void showBodyTemperature(@NonNull String temperature) {
        String bodyTemperature = getString(R.string.format_body_temperature, temperature);
        mBodyTemperatureTextView.setText(bodyTemperature);
    }
}
