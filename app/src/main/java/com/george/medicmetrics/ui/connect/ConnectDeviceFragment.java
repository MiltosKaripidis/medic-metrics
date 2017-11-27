package com.george.medicmetrics.ui.connect;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.george.medicmetrics.ui.base.BaseFragment;

public class ConnectDeviceFragment extends BaseFragment<ConnectDeviceContract.Presenter> {

    private static final String ARG_DEVICE_NAME = "device_name";
    private static final String ARG_DEVICE_ADDRESS = "device_address";

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
}
