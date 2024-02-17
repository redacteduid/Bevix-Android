package com.example.bevixapp;

// IngredientAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> ingredients;
    private int[] presetValues;
    private Context context;
    private IngredientChangeListener listener; // Add listener variable

    public IngredientAdapter(List<Ingredient> ingredients, int[] presetValues, Context context, IngredientChangeListener listener) {
        this.ingredients = ingredients;
        this.presetValues = presetValues;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.imageViewIcon.setImageResource(ingredient.getIconResource());
        holder.textViewName.setText(ingredient.getName());
        holder.textViewCounter.setText(String.valueOf(presetValues[position])); // Set value from preset array

        holder.buttonMinus.setOnClickListener(v -> {
            int mlCount = Integer.parseInt(holder.textViewCounter.getText().toString());
            if (mlCount > 0) {
                mlCount--;
                holder.textViewCounter.setText(String.valueOf(mlCount));
                presetValues[position] = mlCount; // Update array value
                listener.onIngredientQuantityChanged(position, mlCount); // Notify listener
            }
        });

        holder.buttonPlus.setOnClickListener(v -> {
            int mlCount = Integer.parseInt(holder.textViewCounter.getText().toString());
            mlCount++;
            holder.textViewCounter.setText(String.valueOf(mlCount));
            presetValues[position] = mlCount; // Update array value
            listener.onIngredientQuantityChanged(position, mlCount); // Notify listener
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewIcon;
        TextView textViewName;
        Button buttonMinus;
        TextView textViewCounter;
        Button buttonPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.image_view_icon);
            textViewName = itemView.findViewById(R.id.text_view_name);
            buttonMinus = itemView.findViewById(R.id.button_minus);
            textViewCounter = itemView.findViewById(R.id.text_view_counter);
            buttonPlus = itemView.findViewById(R.id.button_plus);
        }
    }

    // Interface to communicate ingredient quantity changes to the activity
    public interface IngredientChangeListener {
        void onIngredientQuantityChanged(int position, int quantity);
    }
}

