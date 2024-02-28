package com.example.bevixapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CupPlacementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cup_placement);

        Button startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> {
            // Get the selected drink name from the intent
            String selectedDrink = getIntent().getStringExtra("drink_name");

            // Generate drink data array based on the selected drink
            int[] presetValues = getIntent().getIntArrayExtra("presetValues");
            int totalSize = getIntent().getIntExtra("total_size", 150);
//             Start Bluetooth activity with the generated drink data array
            Intent bluetoothIntent = new Intent(CupPlacementActivity.this, BluetoothActivity.class);
            bluetoothIntent.putExtra("drink_data_array", presetValues);
            bluetoothIntent.putExtra("total_size", totalSize);
            startActivity(bluetoothIntent);

            // Display QR code based on the data
//            Intent qrIntent = new Intent(CupPlacementActivity.this, DrinkQRActivity.class);
////            qrIntent.putExtra("drink_name", selectedDrink);
//            qrIntent.putExtra("presetValues", presetValues);
//            startActivity(qrIntent);
        });
    }
}

