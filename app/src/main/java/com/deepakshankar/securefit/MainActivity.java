package com.deepakshankar.securefit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.deepakshankar.securefit.util.BluetoothDeviceAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    BluetoothAdapter mBluetoothAdapter;
    public Set<BluetoothDevice> pairedDevies;
    public List<BluetoothDevice> devices;
    ListView pairedDevicesListView;
    BluetoothDeviceAdapter mBluetoothDeviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("SecureFit");
        setSupportActionBar(toolbar);

        //setup of components required to get paired bluetooth devices.
        devices = new ArrayList<>();
        pairedDevicesListView = (ListView) findViewById(R.id.paired_device_listview);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {

        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
            }
            pairedDevies = mBluetoothAdapter.getBondedDevices();
            for (BluetoothDevice btDevice : pairedDevies) {
                devices.add(btDevice);
            }
            mBluetoothDeviceAdapter = new BluetoothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_info, devices);
            pairedDevicesListView.setAdapter(mBluetoothDeviceAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void findNewDevices(View view) {
        Intent scanNewDevicesIntent = new Intent(this, BluetoothActivity.class);
        startActivity(scanNewDevicesIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
