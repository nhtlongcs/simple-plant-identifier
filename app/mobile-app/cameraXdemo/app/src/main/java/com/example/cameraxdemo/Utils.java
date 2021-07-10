package com.example.cameraxdemo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    static ArrayList<List<BoundingBox>> loadJson(Context context){
        ArrayList<List<BoundingBox>> boxes_all = new ArrayList<List<BoundingBox>>();

        for(int i = 49; i >= 0; i--){
            String jsonFileString = getJsonFromAssets(context, "00"+String.format("%03d", i)+".json");
            Gson gson = new Gson();
            Type listUserType = new TypeToken<List<BoundingBox>>() { }.getType();
            List<BoundingBox> boxes = gson.fromJson(jsonFileString, listUserType);
            boxes_all.add(boxes);
        }

//        for (int i = 0; i < boxes.size(); i++) {
//            Log.i("data", "> Item " + i + "\n" + boxes.get(i).getName());
//        }
        return boxes_all;
    }
}