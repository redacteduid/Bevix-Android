package com.example.bevixapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import android.widget.EditText;
import android.widget.TextView;

public class DrinkQRActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_qr);

        // Retrieve the selected drink name and its data array from the intent extras
        String selectedDrink = getIntent().getStringExtra("drink_name");

        // Generate drink data array based on the selected drink
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int[] presetValues = extras.getIntArray("presetValues");
        // Generate and display QR code
//        generateQRCode(selectedDrink, presetValues);

        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        assert presetValues != null;
        generateQRCode(presetValues);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View v) {
//                if (checkPermission()) {
//                    saveQRCodeImage();
//                } else {
//                    requestPermission();
//                }
//            }

            public void onClick(View v) {

//                // Check if extras is not null and presetValues is not null before using them
//                if (presetValues != null) {
//                    // Convert the array to string to display
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (int value : presetValues) {
//                        stringBuilder.append(value).append(", ");
//                    }
//                    generateQRCode(presetValues);
//
//                }
                Intent intent = new Intent(DrinkQRActivity.this, BluetoothActivity.class);
                intent.putExtra("presetValues", presetValues); // Pass presetValues to CupPlacementActivity
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(v -> confirmCancellation());
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveQRCodeImage();
            } else {
                Toast.makeText(this, "Permission denied. Cannot save QR code image.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveQRCodeImage() {
        Bitmap qrCodeBitmap = ((BitmapDrawable) qrCodeImageView.getDrawable()).getBitmap();
        saveDrinkQRCode(qrCodeBitmap);
    }
//
//    private void generateQRCode(String selectedDrink, int[] presetValues) {
//        Log.d("DrinkQRActivity", "Selected Drink: " + selectedDrink);
//        Log.d("DrinkQRActivity", "Drink Data Array: " + Arrays.toString(presetValues));
//        // Check if the selected drink name and its data array are not null
//        if (selectedDrink == null || presetValues == null) {
//            Toast.makeText(this, "Error: Drink data is missing", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Generate QR code for drink data
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        try {
//            StringBuilder drinkData = new StringBuilder();
//            for (int i = 0; i < presetValues.length; i++) {
//                drinkData.append(presetValues[i]);
//                if (i != presetValues.length - 1) {
//                    drinkData.append(",");
//                }
//            }
//            BitMatrix bitMatrix = qrCodeWriter.encode(drinkData.toString(), com.google.zxing.BarcodeFormat.QR_CODE, 400, 400);
//            Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.RGB_565);
//
//            for (int x = 0; x < 400; x++) {
//                for (int y = 0; y < 400; y++) {
//                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
//                }
//            }
//
//            qrCodeImageView.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void generateQRCode(int[] presetValues) {
        // Generate QR code for the preset values
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            StringBuilder drinkData = new StringBuilder();
            for (int i = 0; i < presetValues.length; i++) {
                drinkData.append(presetValues[i]);
                if (i != presetValues.length - 1) {
                    drinkData.append(",");
                }
            }
            BitMatrix bitMatrix = qrCodeWriter.encode(drinkData.toString(), com.google.zxing.BarcodeFormat.QR_CODE, 400, 400);
            Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.RGB_565);

            for (int x = 0; x < 400; x++) {
                for (int y = 0; y < 400; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
                }
            }

            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
        }
    }





    private void saveDrinkQRCode(Bitmap qrCodeBitmap) {
        // Save QR code bitmap to storage
        String displayName = "drink_qr_code.png"; // Specify the file name
        String mimeType = "image/png"; // Specify the MIME type
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        try (OutputStream outputStream = getContentResolver().openOutputStream(imageUri)) {
            qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            Toast.makeText(this, "QR code saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save QR code", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmCancellation() {
        // Confirm cancellation dialog
        new AlertDialog.Builder(this)
                .setTitle("Cancel Drink Sharing")
                .setMessage("Are you sure you want to cancel sharing the drink QR code?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Return to order menu
                        startActivity(new Intent(DrinkQRActivity.this, MenuActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

