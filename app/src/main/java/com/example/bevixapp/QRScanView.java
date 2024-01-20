package com.example.bevixapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;




public class QRScanView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String qrCodeText = extras.getString("qrCodeText");
            // Display the QR code result in your new activity's layout
            TextView textViewResult = findViewById(R.id.textViewResult);
            if (textViewResult != null) {
                textViewResult.setText(qrCodeText);
            }
        }
    }
}

