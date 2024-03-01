package com.example.bevixapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.google.zxing.ResultPoint;
import java.util.Arrays;
import java.util.List;

public class QRScanner extends AppCompatActivity {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    //private int currentCameraId = 0; // 0 for back camera, 1 for front camera

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setStatusText("");

        // Set camera settings to use the front camera
        barcodeScannerView.getBarcodeView().getCameraSettings().setRequestedCameraId(0); // 0 for rear camera or 1 indicates the front camera

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
        barcodeScannerView.decodeSingle(callback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    // BarcodeCallBack
    private final BarcodeCallback callback = result -> {
        // Handle the scanned QR code result here
        String qrCodeText = result.getText();

        // Check if the QR code text is a valid 6-element comma-separated value
        List<String> parts = Arrays.asList(qrCodeText.split(","));
        if (parts.size() != 6) {
            // Display a toast indicating that the QR code is invalid
            Toast.makeText(QRScanner.this, "Invalid QR code", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] item = qrCodeText.split(",");
        int[] presetValues = new int[item.length];
        for (int i = 0; i < item.length; i++) {
            presetValues[i] = Integer.parseInt(item[i].trim());
        }
//        Intent QRintent = new Intent(QRScanner.this, CustomMenuActivity.class);
//        QRintent.putExtra("qrCodeValues", presetValues);
//        startActivity(QRintent);



        // Create an Intent to open the SizePickerActivity and pass the QR code text
        Intent intent = new Intent(QRScanner.this, CustomMenuActivity.class);
        intent.putExtra("qrCodeValues", presetValues);
        startActivity(intent);
    };
}
