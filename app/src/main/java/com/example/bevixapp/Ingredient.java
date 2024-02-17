// Ingredient.java
package com.example.bevixapp;

public class Ingredient {
    private String name;
    private int iconResource;
    private int mlCount;

    public Ingredient(String name, int iconResource) {
        this.name = name;
        this.iconResource = iconResource;
        this.mlCount = 0;
    }

    public String getName() {
        return name;
    }

    public int getIconResource() {
        return iconResource;
    }

    public int getMlCount() {
        return mlCount;
    }

    public void setMlCount(int mlCount) {
        this.mlCount = mlCount;
    }
}
