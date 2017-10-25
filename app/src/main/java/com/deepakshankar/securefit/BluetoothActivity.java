package com.deepakshankar.securefit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.deepakshankar.securefit.util.BluetoothDeviceAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class BluetoothActivity extends AppCompatActivity {

    public static final String TAG = "BluetoothActivity";
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final UUID SECURE_UUID = UUID.fromString("30e8d79b-cfe2-470f-9ebe-a3e90fabd1c7");
    private static final String NAME = "SECUREFIT";
    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevies;
    private List<BluetoothDevice> devices;
    private ListView newDevicesListView;
    private BluetoothDeviceAdapter mBluetoothDeviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.bt_toolbar);
        toolbar.setTitle("New Devices");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        devices = new ArrayList<>();
        newDevicesListView = (ListView) findViewById(R.id.new_device_listview);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {

        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
            }
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            pairedDevies = mBluetoothAdapter.getBondedDevices();
            mBluetoothAdapter.startDiscovery();
            IntentFilter deviceFoundIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(discoverBroadcasrReciever, deviceFoundIntentFilter);
        }

    }

    private BroadcastReceiver discoverBroadcasrReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive: " + action);
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "onReceive: found device " + device.getName() + " : " + device.getAddress());
                if (pairedDevies == null) pairedDevies = new HashSet<>();
                if (!pairedDevies.contains(device)) {
                    devices.add(device);
                    mBluetoothDeviceAdapter = new BluetoothDeviceAdapter(context, R.layout.bluetooth_device_info, devices);
                    newDevicesListView.setAdapter(mBluetoothDeviceAdapter);
                } else {
                    Log.d(TAG, "onReceive: already paired device : " + device.getName() + " : " + device.getAddress());
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (discoverBroadcasrReciever != null) {
            unregisterReceiver(discoverBroadcasrReciever);
        }
    }

    private class BluetoothServerThread extends Thread {
        private final BluetoothServerSocket bluetoothServerSocket;


        public BluetoothServerThread() {
            BluetoothServerSocket temp = null;
            try {
                temp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, SECURE_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket's listen() method failed", e);
            }
            bluetoothServerSocket = temp;
        }

        public void run() {
            BluetoothSocket bluetoothSocket = null;
            while (true) {
                try {
                    bluetoothSocket = bluetoothServerSocket.accept();
                } catch (IOException ioe) {
                    Log.e(TAG, "Socket's accept() method failed", ioe);
                    break;
                }
                if(bluetoothSocket!=null){
                    
                }
            }
        }
    }
}
