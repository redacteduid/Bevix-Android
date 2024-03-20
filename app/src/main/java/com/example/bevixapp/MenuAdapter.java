package com.example.bevixapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuItem> menuItems;
    private OnItemClickListener listener;

    public MenuAdapter(MenuActivity menuActivity, List<MenuItem> menuItems, OnItemClickListener listener) {
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);
        holder.bind(menuItem);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTextView;
        private TextView descriptionTextView;
        private ImageView[] iconImageViews; // Array to hold ImageViews for icons

        private TextView bestTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
//            bestTextView = itemView.findViewById(R.id.best_text_view);
            // Initialize ImageViews for icons
            iconImageViews = new ImageView[5]; // Assuming you have 5 icons per item
            iconImageViews[0] = itemView.findViewById(R.id.icon_image_view1);
            iconImageViews[1] = itemView.findViewById(R.id.icon_image_view2);
            iconImageViews[2] = itemView.findViewById(R.id.icon_image_view3);
            iconImageViews[3] = itemView.findViewById(R.id.icon_image_view4);
            iconImageViews[4] = itemView.findViewById(R.id.icon_image_view5);

            itemView.setOnClickListener(this);
        }

        public void bind(final MenuItem menuItem) {
            nameTextView.setText(menuItem.getName());
            descriptionTextView.setText(menuItem.getDescription());
//            bestTextView.setText(menuItem.getBestText());
            // Set icons
            int[] iconResources = menuItem.getIconResources();
            for (int i = 0; i < iconImageViews.length; i++) {
                if (i < iconResources.length) {
                    iconImageViews[i].setVisibility(View.VISIBLE);
                    iconImageViews[i].setImageResource(iconResources[i]);
                } else {
                    // Hide extra ImageViews if there are fewer icons than expected
                    iconImageViews[i].setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onClick(View v) {
            MenuItem menuItem = menuItems.get(getAdapterPosition());
            listener.onItemClick(menuItem);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }
}
