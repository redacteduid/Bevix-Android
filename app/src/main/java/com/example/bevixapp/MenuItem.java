package com.example.bevixapp;

public class MenuItem {
    private String name;
    private String description;
    private String mix;
    private int[] iconResources; // Array to hold icon resource IDs
    private String best;

    public MenuItem(String name, String description, String mix, int iconResources, String best) {
        this.name = name;
        this.description = description;
        this.mix = mix;
        this.iconResources = new int[]{iconResources};
        this.best = best;

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
    public String getBest() {
        return best;
    }
}
