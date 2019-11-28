package com.example.landmarksapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

class Landmark {
    private int _image;
    private String _name;
    private String _location;

    public Landmark(int image, String name, String location) {
        _image = image;
        _name = name;
        _location = location;
    }

    public int get_image() {
        return _image;
    }

    public String get_location() {
        return _location;
    }

    public String get_name() {
        return _name;
    }

    public static ArrayList<Landmark> setUpLandmarks(Context context) {
        ArrayList<Landmark> landmarks = new ArrayList<Landmark>();
        String json;
        try {
            InputStream is = context.getAssets().open("landmark_values.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject reader = new JSONObject(json);
            JSONArray array = reader.getJSONArray("landmarks");
            for(int i = 0; i < array.length(); i++) {
                String name = array.getJSONObject(i).getString("name");
                String location = array.getJSONObject(i).getString("location");
                String imageName = array.getJSONObject(i).getString("image-name");
                int image = stringToImageResource(context, imageName);
                Landmark newLandmark = new Landmark(image, name, location);
                landmarks.add(newLandmark);
            }
            return landmarks;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static int stringToImageResource(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
