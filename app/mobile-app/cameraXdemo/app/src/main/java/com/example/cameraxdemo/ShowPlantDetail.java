package com.example.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

public class ShowPlantDetail extends AppCompatActivity {
    private ImageView yourImage;
    private TextView plantNam;
    private TextView plantScientificName;
    private TextView description;
    private TextView meaning;
    private TextView care;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant_detail);
        getSupportActionBar().hide();

        yourImage = (ImageView) findViewById(R.id.your_image);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        plantNam = (TextView) findViewById(R.id.plant_name);
        plantScientificName = (TextView) findViewById(R.id.plant_scienctific_name);
        description = (TextView) findViewById(R.id.description);
        meaning = (TextView) findViewById(R.id.meaning);
        care = (TextView) findViewById(R.id.care);

        plantScientificName.setText("Lê Hạnh Linh");

        String name = getIntent().getStringExtra("name");
        String path = getIntent().getStringExtra("path");

        Log.i("TAG", "onCreate: "+path);
        Log.i("TAG", "onCreate: "+name);

        Information information = InformationManager.getInformation(name);
        Plant plant = LabelManager.getPlantByName(name);

        Log.i("TAG", "onCreate: "+plant);

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        yourImage.setImageBitmap(bitmap);
        plantNam.setText(plant.getPlant_name());
        plantScientificName.setText(plant.getPlant_scientific_name());
        description.setText(getResources().getString(information.getDescription()));
        meaning.setText(getResources().getString(information.getMeaning()));
        care.setText(getResources().getString(information.getCare()));
        image1.setImageDrawable(getResources().getDrawable(information.getImages()[0]));
        image2.setImageDrawable(getResources().getDrawable(information.getImages()[1]));
        image3.setImageDrawable(getResources().getDrawable(information.getImages()[2]));
        image4.setImageDrawable(getResources().getDrawable(information.getImages()[3]));
    }
}