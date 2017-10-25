package com.deepakshankar.securefit.util;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deepakshankar.securefit.R;

import java.util.List;

/**
 * Created by DeepakShankar on 10/17/2017.
 */

public class BluetoothDeviceAdapter extends ArrayAdapter {

    private LayoutInflater layoutInflater;
    private List<BluetoothDevice> devices;
    private int viewResourceId;

    public BluetoothDeviceAdapter(Context context, int resourceId, List<BluetoothDevice> devices) {
        super(context, resourceId, devices);
        this.devices = devices;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.viewResourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(viewResourceId, null);
        BluetoothDevice device = devices.get(position);
        if (device != null) {
            TextView device_name = (TextView) convertView.findViewById(R.id.device_name);
            TextView device_address = (TextView) convertView.findViewById(R.id.device_address);

            device_name.setText(device.getName());
            device_address.setText(device.getAddress());
        }

        return convertView;
    }
}
