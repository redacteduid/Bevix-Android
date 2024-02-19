package com.example.bevixapp;

public class MenuItem {
    private String name;
    private String description;
    private String mix;
    private int imageResource;

    public MenuItem(String name, String description, String mix, int imageResource) {
        this.name = name;
        this.description = description;
        this.mix = mix;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMix() {return mix;}

    public int getImageResource() {
        return imageResource;
    }
}


