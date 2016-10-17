package com.example.spotifysample.spotifyResponseModels;

import org.json.JSONObject;

/**
 * Created by Bharath on 08/10/16.
 */
public class Image {

    final String JSON_WIDTH = "width";
    final String JSON_HEIGHT = "height";
    final String JSON_URL = "url";

    private int height;
    private int width;
    private String url;

    public Image(JSONObject imageObject){
        height = imageObject.optInt(JSON_HEIGHT);
        width = imageObject.optInt(JSON_WIDTH);
        url = imageObject.optString(JSON_URL);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }

}
