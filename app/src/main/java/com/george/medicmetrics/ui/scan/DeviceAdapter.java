package com.george.medicmetrics.ui.scan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.medicmetrics.R;
import com.george.medicmetrics.bluetooth.device.Device;

import java.util.List;

class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder> {

    private List<Device> mDeviceList;
    private OnItemClickListener mOnItemClickListener;

    DeviceAdapter(@Nullable List<Device> deviceList, @NonNull OnItemClickListener onItemClickListener) {
        mDeviceList = deviceList;
        mOnItemClickListener = onItemClickListener;
    }

    void setDeviceList(@Nullable List<Device> deviceList) {
        mDeviceList = deviceList;
    }

    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_fragment_scan_device, parent, false);
        return new DeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {
        final Device device = mDeviceList.get(position);

        String deviceName = device.getName();
        holder.nameTextView.setText(deviceName);

        String deviceAddress = device.getAddress();
        holder.addressTextView.setText(deviceAddress);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(device);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList == null ? 0 : mDeviceList.size();
    }

    static class DeviceHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView addressTextView;

        DeviceHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_text_view);
            addressTextView = itemView.findViewById(R.id.address_text_view);
        }
    }

    interface OnItemClickListener {

        void onItemClicked(@NonNull Device device);
    }
}
