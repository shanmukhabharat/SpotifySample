package com.example.spotifysample.spotifyResponseModels.pagingObjectModels;

import com.example.spotifysample.spotifyResponseModels.itemModels.AlbumItem;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * PagingObject that wraps around albums.
 */
public class AlbumPagingObject extends PagingObject {

    private ArrayList<AlbumItem> listOfAlbums;

    public AlbumPagingObject(JSONObject pagingObject) {

        super(pagingObject);

        listOfAlbums = new ArrayList<>();
        for(int i = 0 ; i < getItems().length(); i++){
           JSONObject eachItem =  getItems().optJSONObject(i);
            AlbumItem eachAlbum = new AlbumItem(eachItem);
            listOfAlbums.add(eachAlbum);
        }
    }

    public ArrayList<AlbumItem> getListOfAlbums() {
        return listOfAlbums;
    }
}
