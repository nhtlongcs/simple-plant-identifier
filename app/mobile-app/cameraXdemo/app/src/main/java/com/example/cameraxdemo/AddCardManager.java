package com.example.cameraxdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCardManager {
    private ImageView viewThumbnail;
    private TextView viewPlantName;
    private TextView viewPlantScientificName;
    private TextView viewPlantSun;
    private TextView viewPlantWater;
    private TextView viewPlantFertilizer;
    private ImageButton viewAddButton;
    private ImageButton viewAddCompleteButton;
    private Bitmap subImage;
    private Plant mPlant;

    public AddCardManager(ImageView viewThumbnail, TextView viewPlantName, TextView viewPlantScientificName, TextView viewPlantSun, TextView viewPlantWater, TextView viewPlantFertilizer, ImageButton viewAddButton, ImageButton viewAddCompleteButton) {
        this.viewThumbnail = viewThumbnail;
        this.viewPlantName = viewPlantName;
        this.viewPlantScientificName = viewPlantScientificName;
        this.viewPlantSun = viewPlantSun;
        this.viewPlantWater = viewPlantWater;
        this.viewPlantFertilizer = viewPlantFertilizer;
        this.viewAddButton = viewAddButton;
        this.viewAddCompleteButton = viewAddCompleteButton;
    }

    public void setInfo(Plant plant, Bitmap bitmap){
        mPlant = plant;
        subImage = bitmap;
        viewThumbnail.setImageBitmap(bitmap);
        viewPlantName.setText(plant.getPlant_name());
        viewPlantScientificName.setText(plant.getPlant_scientific_name());
        viewPlantSun.setText(plant.getPlant_sun());
        viewPlantWater.setText(plant.getPlant_water());
        viewPlantFertilizer.setText(plant.getPlant_fertilizer());
    }

    public ImageButton getAddButton(){
        return viewAddButton;
    }

    public ImageButton getAddCompleteButton(){
        return viewAddCompleteButton;
    }

    public List<String> getPlant(){
        List<String> returns = new ArrayList<>();
        mPlant.setThumbnail_path(saveFile());
        returns.add(mPlant.getPlant_name());
        returns.add(mPlant.getThumbnail_path());
        return returns;
    }

    public Bitmap getSubImage(){
        return subImage;
    }

    public String saveFile(){
        String root = Environment.getExternalStorageDirectory().toString();
        Log.i("TAG", "onClick "+root);
        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        String fname = System.currentTimeMillis()+".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            subImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return root+"/saved_images/"+fname;
    }
}
