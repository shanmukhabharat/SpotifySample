package com.example.spotifysample.spotifyResponseModels.itemModels;

import org.json.JSONObject;

/**
 * Class representing the common parameters for
 * all the contents (albums, artists or tracks) of the result object.
 */
public abstract class ItemAbstract {

    final String JSON_ID = "id";
    final String JSON_NAME = "name";
    final String JSON_POPULARITY = "popularity";
    final String JSON_TYPE = "type";
    final String JSON_URI = "uri";
    final String JSON_HREF = "href";
    final String JSON_EXTERNAL_URLS = "external_urls";

    private String id;
    private String name;
    private int popularity;
    private String type;
    private String uri;
    private String href;

    public ItemAbstract(JSONObject obj){
        id = obj.optString(JSON_ID);
        name = obj.optString(JSON_NAME);
        popularity = obj.optInt(JSON_POPULARITY);
        type = obj.optString(JSON_TYPE);
        uri = obj.optString(JSON_URI);
        href = obj.optString(JSON_HREF);
    }

    public String getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
