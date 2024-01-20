package com.example.bevixapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        NumberPicker np = findViewById(R.id.numberPicker);

        np.setMinValue(2);
        np.setMaxValue(20);

        np.setOnValueChangedListener(onValueChangeListener);
    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            Toast.makeText(Order.this, "selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT);
        }
    };
}
