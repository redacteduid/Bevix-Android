package com.example.bevixapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SizePickerActivity extends AppCompatActivity {

    private String selectedDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_picker);

        // Get the selected drink name from the intent
        selectedDrink = getIntent().getStringExtra("drink_name");
        if (selectedDrink == null) {
            selectedDrink = "drink"; // Default to "drink" if null
        }

        Button mediumButton = findViewById(R.id.buttonMedium);
        Button largeButton = findViewById(R.id.buttonLarge);

        mediumButton.setOnClickListener(v -> showCustomizationPrompt(selectedDrink, "Medium"));
        largeButton.setOnClickListener(v -> showCustomizationPrompt(selectedDrink, "Large"));
    }

    private void showCustomizationPrompt(String selectedDrink, String size) {
        int totalSize = 0;
        if (size.equals("Medium")) {
            totalSize = 350; // Set total size for medium
        } else if (size.equals("Large")) {
            totalSize = 450; // Set total size for large
        }

        // Pass the total size to the CustomMenuActivity
        Intent intent = new Intent(SizePickerActivity.this, CustomMenuActivity.class);
        intent.putExtra("drink_name", selectedDrink);
        intent.putExtra("cup_size", size);
        intent.putExtra("total_size", totalSize); // Pass total size to CustomMenuActivity
        startActivity(intent);
    }
}
