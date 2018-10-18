package com.example.aftermath.project_baskara;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="Main Activity";
    ToggleButton tbBluetooth;
    Button btnDiscover;
    Button btnDiscoverNearby;
    ListView lvDevice;
    BluetoothAdapter bluetoothAdapter;
    public ArrayList<BluetoothDevice> listBluetoothDevice = new ArrayList<>();
    public ArrayList arrayListStrength = new ArrayList();
    public DeviceListAdapter deviceListAdapter;

    private void initViews(){
        btnDiscover = findViewById(R.id.btn_discover);
        btnDiscoverNearby = findViewById(R.id.btn_discover_nearby);
        lvDevice = findViewById(R.id.lv_device);
        tbBluetooth = findViewById(R.id.tb_bluetooth);
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = getIntent().getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "onReceive: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "onReceive: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "onReceive: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                int mode = getIntent().getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode){
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "onReceive: DISCOVERABILITY ENABLED.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "onReceive: ABLE TO RECEIVE CONNECTION");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "onReceive: NOT ABLE TO RECEIVE CONNECTION");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "onReceive: CONNECTING");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "onReceive: CONNECTED");
                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                int strength = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                listBluetoothDevice.add(device);
                ////
                arrayListStrength.add(strength);

                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                deviceListAdapter = new DeviceListAdapter(context, R.layout.adapter_device, listBluetoothDevice, arrayListStrength);
                lvDevice.setAdapter(deviceListAdapter);
            }
        }
    };

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: CALLED.");
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        unregisterReceiver(mBroadcastReceiver2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: CALLED.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        tbBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: TOGGLE BUTTON CLICKED");
                if(tbBluetooth.isChecked()){
                    Log.d(TAG, "onClick: BT ENABLED");
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                    IntentFilter btIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                    registerReceiver(mBroadcastReceiver, btIntent);
                }else{
                    Log.d(TAG, "onClick: BT DISABLED");
                    bluetoothAdapter.disable();
                    IntentFilter btIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                    registerReceiver(mBroadcastReceiver, btIntent);
                    Toast.makeText(getApplicationContext(), "Bluetooth turned off" ,Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: BUTTON DISCOVERABLE CLICKED");
                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                getVisible.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivityForResult(getVisible, 0);

                IntentFilter intentFilter = new IntentFilter(bluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
                registerReceiver(mBroadcastReceiver2, intentFilter);
            }
        });

        btnDiscoverNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: LOOKING FOR NEARBY DEVICE");
                if(bluetoothAdapter.isDiscovering()){
                    bluetoothAdapter.cancelDiscovery();
                    Log.d(TAG, "onClick: CANCEL DISCOVERY");
                    bluetoothAdapter.startDiscovery();

                    checkBluetoothPermission();

                    IntentFilter intentFilter= new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mBroadcastReceiver3, intentFilter);
                }

                if(!bluetoothAdapter.isDiscovering()){
                    checkBluetoothPermission();
                    bluetoothAdapter.startDiscovery();
                    IntentFilter intentFilter= new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mBroadcastReceiver3, intentFilter);

                }
            }
        });
    }//oncreate

    private void checkBluetoothPermission(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
}
