package com.smartchannel.bluetoothtest;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    //Android 12 easy
    private boolean inAndroid12 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;

    private boolean isBluetoothScanNotok() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean isBluetoothConnectNotok() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        ListView listView;
        int REQUEST_ENABLE_BT = 0;

        Button scanBtn;

        TextView textView;

        //TextView textView = (TextView) findViewById(R.id.PeripheralTextView);
        scanBtn = findViewById(R.id.StartScanButton);
        listView = findViewById(R.id.listview);
        textView = findViewById(R.id.textView);
        //textView.setText(getlocalBluetoothName());
        if (isBluetoothScanNotok()) {
            ActivityResultLauncher<String> requestPermissionLauncher =
                    registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                        if (isGranted) {
                            // Permission is granted. Continue the action or workflow in your
                            // app.
                        } else {
                            // Explain to the user that the feature is unavailable because the
                            // feature requires a permission that the user has denied. At the
                            // same time, respect the user's decision. Don't link to system
                            // settings in an effort to convince the user to change their
                            // decision.
                        }
                    });
            requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_SCAN);
        } else if (isBluetoothConnectNotok()) {
            ActivityResultLauncher<String> requestPermissionLauncher =
                    registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                        if (isGranted) {
                            // Permission is granted. Continue the action or workflow in your
                            // app.
                        } else {
                            // Explain to the user that the feature is unavailable because the
                            // feature requires a permission that the user has denied. At the
                            // same time, respect the user's decision. Don't link to system
                            // settings in an effort to convince the user to change their
                            // decision.
                        }
                    });
            requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT);
        }


        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                //registerReceiver(receiver, filter);
                if (bluetoothAdapter == null) {
                    // Device doesn't support Bluetooth

                } else {
                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }
                    bluetoothAdapter.startDiscovery();
                    ArrayList<String> arrayList = new ArrayList<>();
                    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

                        @Override
                        public void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();
                            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                Log.i("Bluetooth Device ", device.getName());
                                arrayList.add(device.getName());


                            }
                        }
                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
                                listView.setAdapter(itemsAdapter);
                    };

                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_ADMIN)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN} , 2);

        }
        //
    }

}

