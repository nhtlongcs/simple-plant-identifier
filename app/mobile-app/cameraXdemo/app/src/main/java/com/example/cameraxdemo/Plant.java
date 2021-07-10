package com.example.cameraxdemo;

public class Plant {
    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public String getPlant_scientific_name() {
        return plant_scientific_name;
    }

    public void setPlant_scientific_name(String plant_scientific_name) {
        this.plant_scientific_name = plant_scientific_name;
    }

    public String getPlant_sun() {
        return plant_sun;
    }

    public void setPlant_sun(String plant_sun) {
        this.plant_sun = plant_sun;
    }

    public String getPlant_water() {
        return plant_water;
    }

    public void setPlant_water(String plant_water) {
        this.plant_water = plant_water;
    }

    public String getPlant_fertilizer() {
        return plant_fertilizer;
    }

    public void setPlant_fertilizer(String plant_fertilizer) {
        this.plant_fertilizer = plant_fertilizer;
    }

    public Plant(String thumbnail_path, String plant_name, String plant_scientific_name, String plant_sun, String plant_water, String plant_fertilizer) {
        this.thumbnail_path = thumbnail_path;
        this.plant_name = plant_name;
        this.plant_scientific_name = plant_scientific_name;
        this.plant_sun = plant_sun;
        this.plant_water = plant_water;
        this.plant_fertilizer = plant_fertilizer;
    }

    private String thumbnail_path;
    private String plant_name;
    private String plant_scientific_name;
    private String plant_sun;
    private String plant_water;
    private String plant_fertilizer;
}
