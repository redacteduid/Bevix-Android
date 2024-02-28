package com.example.bevixapp;

public class MenuItem {
    private String name;
    private String description;
    private String mix;
    private int[] iconResources; // Array to hold icon resource IDs

    public MenuItem(String name, String description, String mix, int iconResources) {
        this.name = name;
        this.description = description;
        this.mix = mix;
        this.iconResources = new int[]{iconResources};
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMix() {
        return mix;
    }

    public int[] getIconResources() {
        return iconResources;
    }
}
