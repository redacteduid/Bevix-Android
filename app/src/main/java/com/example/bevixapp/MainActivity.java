package com.example.bevixapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button ConnectBT, ScanQR, Order, QRGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectBT = findViewById(R.id.ConnectBT);
        ScanQR = findViewById(R.id.ScanQR);
        Order = findViewById(R.id.Order);
        QRGenerator = findViewById(R.id.QRGenerator);

        ConnectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, BluetoothActivity.class);
                startActivity(intent);
            }
        });


        ScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, QRScanner.class);
                startActivity(intent);
            }
        });

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, Menu.class);
                startActivity(intent);
            }
        });

        QRGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, QRGenerator.class);
                startActivity(intent);
            }
        });

    }}
