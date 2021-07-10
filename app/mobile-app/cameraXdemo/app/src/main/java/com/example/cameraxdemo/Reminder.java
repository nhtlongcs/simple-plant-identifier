package com.example.cameraxdemo;

public class Reminder {
    private Plant mPlant;
    private int mTypeOfAction;
    private String mDay;
    private String mDescription;

    public Reminder(Plant mPlant, int mTypeOfAction, String mDay, String mDescription) {
        this.mPlant = mPlant;
        this.mTypeOfAction = mTypeOfAction;
        this.mDay = mDay;
        this.mDescription = mDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getIcon(){
        int id = 0;
        switch (mTypeOfAction){
            case 0:
                id = R.drawable.icon_sun;
                break;
            case 1:
                id = R.drawable.icon_water;
                break;
            case 2:
                id = R.drawable.icon_fertilizer;
                break;
        }
        return id;
    }

    public Plant getPlant() {
        return mPlant;
    }

    public void setPlant(Plant mPlant) {
        this.mPlant = mPlant;
    }

    public int getTypeOfAction() {
        return mTypeOfAction;
    }

    public void setTypeOfAction(int mTypeOfAction) {
        this.mTypeOfAction = mTypeOfAction;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        this.mDay = day;
    }
}
