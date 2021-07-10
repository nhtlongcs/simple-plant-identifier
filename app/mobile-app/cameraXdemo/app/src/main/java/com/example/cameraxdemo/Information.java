package com.example.cameraxdemo;

public class Information {

    private int description;
    private int meaning;
    private int care;
    private int[] images;

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getMeaning() {
        return meaning;
    }

    public void setMeaning(int meaning) {
        this.meaning = meaning;
    }

    public int getCare() {
        return care;
    }

    public void setCare(int care) {
        this.care = care;
    }

    public int[] getImages() {
        return images;
    }

    public void setImages(int[] images) {
        this.images = images;
    }

    public Information(int description, int meaning, int care, int[] images) {
        this.description = description;
        this.meaning = meaning;
        this.care = care;
        this.images = images;
    }
}
