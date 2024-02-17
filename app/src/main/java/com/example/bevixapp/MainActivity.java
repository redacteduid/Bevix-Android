package com.example.bevixapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button ScanQR, Order, ConnectBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectBluetooth = findViewById(R.id.Connect);
        ScanQR = findViewById(R.id.ScanQR);
        Order = findViewById(R.id.Order);

        ConnectBluetooth.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
            startActivity(intent);
        });

        ScanQR.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, QRScanner.class);
            startActivity(intent);
        });

        Order.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        });

    }
}

