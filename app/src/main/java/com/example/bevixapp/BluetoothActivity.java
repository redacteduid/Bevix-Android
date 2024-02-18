package com.example.bevixapp;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

    private static final String TAG = "BluetoothActivity";
    private static final UUID HC05_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_PERMISSION_BLUETOOTH = 2;
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice hc05Device;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check Bluetooth permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_PERMISSION_BLUETOOTH);
        } else {
            initBluetooth();
        }
    }

    private void initBluetooth() {
        // Check Bluetooth permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            // Request Bluetooth permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_PERMISSION_BLUETOOTH);
            return;
        }

        // Initialize Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.e(TAG, "Bluetooth not supported");
            finish();
            return;
        }

        // Check if Bluetooth is enabled, if not, prompt the user to enable it
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            // Bluetooth is enabled, initiate the pairing process
            initiatePairing();
        }
    }

    // Handle the result of Bluetooth enabling request
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is enabled, initiate the pairing process
                initiatePairing();
            } else {
                // Bluetooth enabling was canceled or failed
                Log.e(TAG, "Bluetooth enabling canceled or failed");
                Toast.makeText(this, "Bluetooth is required for the app to function", Toast.LENGTH_SHORT).show();
                // Return to the main activity
                finish();
            }
        }
    }

    // Handle the result of permission requests
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_BLUETOOTH) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initBluetooth();
            } else {
                Toast.makeText(this, "Bluetooth permissions denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void initiatePairing() {
        // Find paired HC-05 device
        hc05Device = findPairedDevice("00:22:09:01:A0:69"); // Replace with your HC-05 MAC address
        if (hc05Device == null) {
            // Device is not paired, show a toast indicating the pairing process
            Toast.makeText(this, "Pairing with Bluetooth device...", Toast.LENGTH_SHORT).show();
            // Perform the pairing process here (you can use Bluetooth pairing methods)
        } else {
            // Device is already paired, connect to it
            connectToDevice();
            Toast.makeText(this, "Already Paired", Toast.LENGTH_SHORT).show();
        }
    }

    private BluetoothDevice findPairedDevice(String address) {
        // Check if permissions are granted
        if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            // Permissions not granted, request them
            requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_BLUETOOTH_PERMISSIONS);
            return null; // Cannot perform operation until permissions are granted
        }

        // Permissions granted, proceed with finding paired device
        for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
            if (device.getAddress().equals(address)) {
                return device;
            }
        }
        return null;
    }

    private void connectToDevice() {
        // Check if permissions are granted
        if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            // Permissions not granted, request them
            requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_BLUETOOTH_PERMISSIONS);
            return; // Cannot perform operation until permissions are granted
        }

        try {
            socket = hc05Device.createRfcommSocketToServiceRecord(HC05_UUID);
            socket.connect();
            outputStream = socket.getOutputStream();
            Log.d(TAG, "Connected to Bevix");
            // Add code here to show a toast indicating paired and connected
            Toast.makeText(this, "Paired and connected to Bevix", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Bevix: " + e.getMessage());
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing Bluetooth socket: " + e.getMessage());
        }
    }

    private void sendDataOverBluetooth(int[] data) {
        // Check if the Bluetooth socket is available
        if (outputStream == null) {
            Log.e(TAG, "Output stream is not available");
            return;
        }

        // Create a StringBuilder to build the comma-separated string
        StringBuilder sb = new StringBuilder();

        // Limit to 6 elements
        int maxLength = Math.min(data.length, 6);
        for (int i = 0; i < maxLength; i++) {
            sb.append(data[i]);
            if (i < maxLength - 1) { // Append comma for all elements except the last one
                sb.append(",");
            }
        }

        // Convert StringBuilder to String
        String dataString = sb.toString();

        try {
            // Send data over Bluetooth
            outputStream.write(dataString.getBytes());
            Log.d(TAG, "Sent data: " + dataString);
        } catch (IOException e) {
            Log.e(TAG, "Error sending data: " + e.getMessage());
        }
    }

}
