package com.example.cameraxdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowPhotoActivity extends AppCompatActivity {

    private AddCardManager addCardManager;
    private CardView addCardView;
    private ImageView imageView;
    private CustomView customView;
    private ArrayList<String> paths;
    private String isGallery;
    private Slider slider;
    private int numberOfImages;
    private int currentImage;
    private int imageHeight;
    private int imageWidth;
    private ArrayList<ArrayList<Rect>> bounds;
    private ArrayList<ArrayList<String>> classes;
    private ImageButton addButton;
    private ImageButton addCompleteButton;
    private ImageButton toGardenButton;
    private ArrayList<String> addedClasses;
    private ArrayList<String> addedPaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        //hide appbar
        getSupportActionBar().hide();

        initialzie();

        //get json to draw rectangle
        getJson();

        //pass data to customview
        customView.setInits(paths, bounds, classes, addCardManager, addCardView);

        //set first image to image view and index
        setImage(0);
        currentImage = 0;

        addedClasses = new ArrayList<>();
        addedPaths = new ArrayList<>();

        //set listener to add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> plant = addCardManager.getPlant();
                addedClasses.add(plant.get(0));
                addedPaths.add(plant.get(1));
                addCompleteButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.GONE);
            }
        });

        toGardenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPhotoActivity.this,
                        MainActivity.class);
                intent.putExtra("addedPaths", addedPaths);
                intent.putExtra("addedClasses", addedClasses);
                startActivity(intent);
            }
        });

        //add on change listener for slider
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if ((int) Math.floor(value) != currentImage) {
                    currentImage = (int) Math.floor(value);
                    setImage((int) Math.floor(value));
                }
            }
        });

        addCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plant_name = ((TextView) findViewById(R.id.plant_name)).getText().toString().trim();
                Intent intent = new Intent(ShowPhotoActivity.this, ShowPlantDetail.class);
                intent.putExtra("name", plant_name);
                intent.putExtra("path", addCardManager.saveFile());
                startActivity(intent);
            }
        });
    }

    private void initialzie() {
        imageView = (ImageView) findViewById(R.id.image_view);
        addCardView = (CardView) findViewById(R.id.add_card);
        slider = (Slider) findViewById(R.id.slider);
        customView = (CustomView) findViewById(R.id.canvas);
        addButton = (ImageButton) findViewById(R.id.btn_add);
        addCompleteButton = (ImageButton) findViewById(R.id.btn_add_complete);
        toGardenButton = (ImageButton) findViewById(R.id.btn_to_garden);

        //hide add card
        addCardView.setVisibility(View.GONE);
        addButton.setVisibility(View.GONE);
        addCompleteButton.setVisibility(View.GONE);

        //initialize add CardManager
        ImageView viewThumbnail = (ImageView) findViewById(R.id.thumbnail);
        TextView viewPlantName = (TextView) findViewById(R.id.plant_name);
        TextView viewPlantScientificName = (TextView) findViewById(R.id.plant_scienctific_name);
        TextView viewPlantSun = (TextView) findViewById(R.id.plant_sun);
        TextView viewPlantWater = (TextView) findViewById(R.id.plant_water);
        TextView viewPlantFertilizer = (TextView) findViewById(R.id.plant_fertilizer);
        addCardManager = new AddCardManager(viewThumbnail, viewPlantName, viewPlantScientificName, viewPlantSun, viewPlantWater, viewPlantFertilizer, addButton, addCompleteButton);

        //get extras from intent
        paths = (ArrayList<String>) getIntent().getSerializableExtra("paths");
        isGallery = getIntent().getStringExtra("isGallery");
        numberOfImages = paths.size();
        slider.setValueTo(numberOfImages - 1);

        //if only 1 image --> hide slider
        if (numberOfImages == 1) {
            slider.setVisibility(View.GONE);
        }

        //initialize bounding box
        bounds = new ArrayList<ArrayList<Rect>>(paths.size());
        for (int i = 0; i < paths.size(); i++)
            bounds.add(new ArrayList<Rect>());

        classes = new ArrayList<ArrayList<String>>(paths.size());
        for (int i = 0; i < paths.size(); i++)
            classes.add(new ArrayList<String>());
    }

    private void uploadImage(String path, int index) {
        File file = new File(path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        ApiService.apiService.getBoundingBox(part).enqueue(new Callback<BoundingBox[]>() {
            @Override
            public void onResponse(Call<BoundingBox[]> call, Response<BoundingBox[]> response) {
                ArrayList<Rect> bound = new ArrayList<Rect>();
                ArrayList<String> classe = new ArrayList<String>();
                for (int i = 0; i < response.body().length; i++) {
                    classe.add(response.body()[i].getName());
                    Rect rect00 = new Rect(response.body()[i].getXmin(), response.body()[i].getYmin(), response.body()[i].getXmax(), response.body()[i].getYmax());
                    bound.add(rect00);
                }
                if(bound.size() == 0){
                    classe.add("");
                    bound.add(new Rect(0,0,0,0));
                }

                classes.set(index, classe);
                bounds.set(index, bound);
                customView.setAsyncBounds(bounds, classes, imageHeight, imageWidth, index);
            }

            @Override
            public void onFailure(Call<BoundingBox[]> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t);
//                Toast.makeText(ShowPhotoActivity.this, "Call api failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getJson() {
        if (paths.size() == 50) {
            //Read json
            ArrayList<List<BoundingBox>> boxes = Utils.loadJson(getApplicationContext());
            for (int i = 0; i < boxes.size(); i++) {
                ArrayList<Rect> bound = new ArrayList<Rect>();
                ArrayList<String> classe = new ArrayList<String>();
                for (int j = 0; j < boxes.get(i).size(); j++) {
                    Rect rect00 = new Rect(boxes.get(i).get(j).getXmin(), boxes.get(i).get(j).getYmin(), boxes.get(i).get(j).getXmax(), boxes.get(i).get(j).getYmax());
                    bound.add(rect00);
                    classe.add(boxes.get(i).get(j).getName());
                }
                classes.set(i, classe);
                bounds.set(i, bound);
            }
            customView.setReady();
        } else {
            for (int i = 0; i < paths.size(); i++)
                uploadImage(paths.get(i), i);
        }
    }

    private void setImage(int index) {
        Bitmap myBitmap = BitmapFactory.decodeFile(paths.get(index));
        if (isGallery.equals("0"))
            myBitmap = RotateBitmap(myBitmap, 90);
        imageHeight = myBitmap.getHeight();
        imageWidth = myBitmap.getWidth();
        imageView.setImageBitmap(myBitmap);
        customView.drawBoundingBox(index, imageHeight, imageWidth);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}