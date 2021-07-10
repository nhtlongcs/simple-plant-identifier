package com.example.cameraxdemo;

import com.google.gson.annotations.SerializedName;

public class BoundingBox {
    private float xmin;
    private float xmax;
    private float ymin;
    private float ymax;

    public BoundingBox(float xmin, float xmax, float ymin, float ymax) {
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }

    public BoundingBox(BoundingBox bb) {
        this.xmin = bb.getXmin();
        this.xmax = bb.getXmax();
        this.ymin = bb.getYmin();
        this.ymax = bb.getYmax();
    }

    private String name;

    public int getXmin() {
        return (int) xmin;
    }

    public void setXmin(float xmin) {
        this.xmin = xmin;
    }

    public int getXmax() {
        return (int) xmax;
    }

    public void setXmax(float xmax) {
        this.xmax = xmax;
    }

    public int getYmin() {
        return (int) ymin;
    }

    public void setYmin(float ymin) {
        this.ymin = ymin;
    }

    public int getYmax() {
        return (int) ymax;
    }

    public void setYmax(float ymax) {
        this.ymax = ymax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
