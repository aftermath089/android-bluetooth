package com.example.aftermath.project_baskara;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<BluetoothDevice> mDevices;
    private ArrayList strength;
    private int  mViewResourceId;

    public DeviceListAdapter(Context context, int tvResourceId, ArrayList<BluetoothDevice> devices, ArrayList strength){
        super(context, tvResourceId,devices);
        this.mDevices = devices;
        this.strength = strength;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = tvResourceId;
    }

    public View getView(int position, View view, ViewGroup parent) {
        view = mLayoutInflater.inflate(mViewResourceId, null);

        BluetoothDevice device = mDevices.get(position);
        String strengthy = strength.get(position).toString()+" dBm";

        if (device != null) {
            TextView deviceName = view.findViewById(R.id.tv_device_name);
            TextView deviceAdress = view.findViewById(R.id.tv_device_address);
            TextView deviceStrength = view.findViewById(R.id.tv_signal_strength);


            if (deviceName != null) {
                deviceName.setText(device.getName());
            }
            if (deviceAdress != null) {
                deviceAdress.setText(device.getAddress());
            }
            if(deviceStrength !=null){
                deviceStrength.setText(strengthy);
            }
        }

        return view;
    }

}