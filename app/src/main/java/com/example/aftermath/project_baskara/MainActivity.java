package com.example.aftermath.project_baskara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ToggleButton tbBluetooth;
    Button btnDiscover;
    ListView lvDevice;
    ArrayList arrayList = new ArrayList();
    BluetoothAdapter bluetoothAdapter;

    private void initViews(){
        btnDiscover = findViewById(R.id.btn_discover);
        lvDevice = findViewById(R.id.lv_device);
        tbBluetooth = findViewById(R.id.tb_bluetooth);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayList);
        lvDevice.setAdapter(arrayAdapter);

        tbBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tbBluetooth.isChecked()){
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                }else{
                    bluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(), "Bluetooth turned off" ,Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(getVisible, 0);
            }
        });
    }


}
