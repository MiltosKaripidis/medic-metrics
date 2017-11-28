package com.george.medicmetrics.ui.connect;

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
import android.widget.Toast;

import com.george.medicmetrics.data.BluetoothService;
import com.george.medicmetrics.ui.base.BaseFragment;

public class ConnectDeviceFragment extends BaseFragment<ConnectDeviceContract.Presenter> {

    private static final String ARG_DEVICE_NAME = "device_name";
    private static final String ARG_DEVICE_ADDRESS = "device_address";
    private BluetoothService mBluetoothService;
    private boolean mBound;
    private String mDeviceName;
    private String mDeviceAddress;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BluetoothService.LocalBinder localBinder = (BluetoothService.LocalBinder) service;
            mBluetoothService = localBinder.getService();
            mBluetoothService.initialize();
            mBluetoothService.connect(mDeviceAddress);
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
                case BluetoothService.ACTION_GATT_CONNECTED:
                    Toast.makeText(context, "Connected!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @NonNull
    @Override
    protected ConnectDeviceContract.Presenter createPresenter() {
        return new ConnectDevicePresenter();
    }

    public static ConnectDeviceFragment newInstance(@NonNull String deviceName,
                                                    @NonNull String deviceAddress) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DEVICE_NAME, deviceName);
        bundle.putString(ARG_DEVICE_ADDRESS, deviceAddress);

        ConnectDeviceFragment fragment = new ConnectDeviceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeviceName = getArguments().getString(ARG_DEVICE_NAME);
        mDeviceAddress = getArguments().getString(ARG_DEVICE_ADDRESS);

        Intent intent = BluetoothService.newIntent(getContext());
        getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mGattReceiver, createGattIntentFilter());
        if (mBluetoothService != null) {
            mBluetoothService.connect(mDeviceAddress);
        }
    }

    private IntentFilter createGattIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothService.ACTION_DATA_AVAILABLE);
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
        mBluetoothService = null;
        super.onDestroy();
    }
}
