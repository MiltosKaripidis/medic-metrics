package com.george.medicmetrics.ui.scan;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.medicmetrics.R;
import com.george.medicmetrics.behavior.adapter.Adapter;
import com.george.medicmetrics.behavior.device.Device;
import com.george.medicmetrics.injection.Injection;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.metrics.MetricsActivity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ScanDeviceFragment extends BaseFragment<ScanDeviceContract.Presenter> implements ScanDeviceContract.View {

    private static final int PERMISSION_ACCESS_FINE_LOCATION = 0;
    private DeviceAdapter mDeviceAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mEmptyTextView;

    public static ScanDeviceFragment newInstance() {
        return new ScanDeviceFragment();
    }

    @NonNull
    @Override
    protected ScanDeviceContract.Presenter createPresenter() {
        Handler handler = new Handler();
        Adapter bluetoothScanBehavior = Injection.provideAdapter(getContext());
        Executor executor = Executors.newSingleThreadExecutor();

        return new ScanDevicePresenter(handler, bluetoothScanBehavior, executor);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_device, container, false);

        setupRefreshLayout(view);
        mEmptyTextView = view.findViewById(R.id.empty_text_view);
        setupAdapter();
        setupRecyclerView(view);

        return view;
    }

    private void setupRefreshLayout(@NonNull View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDeviceAdapter.setDeviceList(null);
                mDeviceAdapter.notifyDataSetChanged();

                mPresenter.scanDevices();
            }
        });
    }

    private void setupAdapter() {
        mDeviceAdapter = new DeviceAdapter(null, new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(@NonNull Device device) {
                mPresenter.stopScanning();
                Intent intent = MetricsActivity.newIntent(getContext(), device.getName(), device.getAddress());
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mDeviceAdapter);
    }

    @Override
    public void showDevices(@NonNull List<Device> deviceList) {
        mDeviceAdapter.setDeviceList(deviceList);
        mDeviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressIndicator() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressIndicator() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void openBluetoothSettings(int requestCode) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivity(intent);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public boolean needsRuntimePermission() {
        return Build.VERSION.SDK_INT >= 23;
    }

    @Override
    public boolean hasFineLocationPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestFineLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
    }

    @Override
    public boolean isGpsEnabled() {
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void showLocationEnableDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("Bluetooth needs the GPS enabled in order to scan devices properly.")
                .setCancelable(false)
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showEmptyDevices() {
        mEmptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyDevices() {
        mEmptyTextView.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                mPresenter.handleAccessFineLocationPermissionResult(grantResults);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.tryToScanDevices();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        mPresenter.handleSettingsResult(requestCode, resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        mPresenter.stopScanning();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
