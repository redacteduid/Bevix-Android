package com.example.bevixapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements MenuAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private Button customButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.menuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(getString(R.string.drink_purple), getString(R.string.description_purple), R.drawable.ic_launcher_background));
        menuItems.add(new MenuItem(getString(R.string.drink_lemon_lime), getString(R.string.description_lemon_lime), R.drawable.ic_launcher_background));
        menuItems.add(new MenuItem(getString(R.string.drink_strawberry_apple), getString(R.string.description_strawberry_apple), R.drawable.ic_launcher_background));
        menuItems.add(new MenuItem(getString(R.string.drink_strawberry_tea), getString(R.string.description_strawberry_tea), R.drawable.ic_launcher_background));
        menuItems.add(new MenuItem(getString(R.string.drink_apple_tea), getString(R.string.description_apple_tea), R.drawable.ic_launcher_background));

        adapter = new MenuAdapter(this, menuItems, this); // Pass 'this' as the listener
        recyclerView.setAdapter(adapter);

        // Get a reference to the custom button
        customButton = findViewById(R.id.customButton);

        // Set click listener for the custom button
        customButton.setOnClickListener(v -> {
            // Handle custom button click here
            Intent intent = new Intent(MenuActivity.this, SizePickerActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemClick(MenuItem item) {
        // Handle item click here
        Intent intent = new Intent(MenuActivity.this, SizePickerActivity.class);
        intent.putExtra("drink_name", item.getName());
        intent.putExtra("drink_description", item.getDescription());
        startActivity(intent);
    }
}
