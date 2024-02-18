package com.example.bevixapp;

// CustomMenuActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class    CustomMenuActivity extends AppCompatActivity implements IngredientAdapter.IngredientChangeListener {

    private RecyclerView recyclerView;
    private Button buttonDone;

    private List<Ingredient> ingredients;
    private int[] presetValues; // Array to store ml counts for each ingredient
    private int totalSize = 0; // Variable to store the total size

    private IngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_menu);

        // Retrieve preset values from intent extras

        int[] qrCodeValues = getIntent().getIntArrayExtra("qrCodeValues");


        // Initialize views
        recyclerView = findViewById(R.id.recycler_view_ingredients);
        buttonDone = findViewById(R.id.button_done);

        // Get total size selected from SizePickerActivity
        totalSize = getIntent().getIntExtra("total_size", 0);

        // Initialize ingredients
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Tonic Water", R.drawable.ic_launcher_background));
        ingredients.add(new Ingredient("Lime Juice", R.drawable.ic_launcher_background));
        ingredients.add(new Ingredient("Butterfly Pea", R.drawable.ic_launcher_background));
        ingredients.add(new Ingredient("Lemon Juice", R.drawable.ic_launcher_background));
        ingredients.add(new Ingredient("Strawberry", R.drawable.ic_launcher_background));
        ingredients.add(new Ingredient("Green Apple", R.drawable.ic_launcher_background));

        // Initialize preset values with 6 zeros initially
//        presetValues = new int[]{0, 0, 0, 0, 0, 0};
        presetValues = qrCodeValues != null ? qrCodeValues : new int[]{0, 0, 0, 0, 0, 0};
//        if (qrCodeValues != null){
//            presetValues =  new int[] qrCodeValues;
//        } else {
//            presetValues = new int[]{0, 0, 0, 0, 0, 0};
//        }
        // Initialize RecyclerView
        adapter = new IngredientAdapter(ingredients, presetValues, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set click listener for "Done" button
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Proceed to CupPlacementActivity
                Intent intent = new Intent(CustomMenuActivity.this, CupPlacementActivity.class);
                intent.putExtra("presetValues", presetValues); // Pass presetValues to CupPlacementActivity
                startActivity(intent);

                Intent secondIntent = new Intent(CustomMenuActivity.this, DrinkQRActivity.class);
                secondIntent.putExtra("presetValues", presetValues); // Pass presetValues to CupPlacementActivity
                startActivity(secondIntent);
            }
        });
    }

    // Method to update presetValues array when an ingredient is incremented or decremented
    @Override
    public void onIngredientQuantityChanged(int position, int quantity) {
        presetValues[position] = quantity;
    }
}
