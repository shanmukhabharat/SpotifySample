package com.example.spotifysample.spotifyResponseModels.itemModels;

import com.example.spotifysample.spotifyResponseModels.Image;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class representing each album item and its contents.
 */
public class AlbumItem extends ItemAbstract{

    final String JSON_ALBUM_TYPE = "album_type";
    final String JSON_ARTISTS = "artists";
    final String JSON_AVAILABLE_MARKETS = "available_markets";
    final String JSON_COPY_RIGHTS = "copyrights";
    final String JSON_EXTERNAL_IDS = "external_ids";
    final String JSON_GENRE = "genres";
    final String JSON_IMAGES = "images";
    final String JSON_LABEL = "label";
    final String JSON_RELEASE_DATE = "release_date";
    final String JSON_RELEASE_DATE_PRECISION = "release_date_precision";
    final String JSON_TRACKS = "tracks";


    private String albumType;
    private ArrayList<String> availableMarkets;
    private ArrayList<String> genres;
    private ArrayList<Image> images;
    private String label;
    private String releaseDate;
    private  String releaseDatePrecision;


    public AlbumItem(JSONObject obj) {

        super(obj);

        albumType = obj.optString(JSON_ALBUM_TYPE);
        label = obj.optString(JSON_LABEL);

        images = new ArrayList<>();
        JSONArray imagesArray = obj.optJSONArray(JSON_IMAGES);
        if(imagesArray != null){
            for(int i = 0; i < imagesArray.length(); i++){
                JSONObject eachImageObj = imagesArray.optJSONObject(i);
                Image eachImage = new Image(eachImageObj);
                images.add(eachImage);
            }
        }

        releaseDate = obj.optString(JSON_RELEASE_DATE);
        releaseDatePrecision = obj.optString(JSON_RELEASE_DATE_PRECISION);
    }

    public String getAlbumType() {
        return albumType;
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

}
