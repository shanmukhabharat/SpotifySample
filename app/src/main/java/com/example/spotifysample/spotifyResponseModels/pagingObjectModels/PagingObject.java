package com.example.spotifysample.spotifyResponseModels.pagingObjectModels;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class representing PagingObject.
 * Main responseObject which wraps around albums, artists and tracks
 */
public abstract class PagingObject {

    final String JSON_LIMIT = "limit";
    final String JSON_NEXT = "next";
    final String JSON_OFFSET = "offset";
    final String JSON_PREVIOUS = "previous";
    final String JSON_TOTAL = "total";
    final String JSON_HREF = "href";
    final String JSON_ITEMS = "items";

    private int offset;
    private int total;
    private int limit;
    private String next;
    private String previous;
    private String href;
    private JSONArray items;


    public PagingObject(JSONObject pagingObject){

        offset = pagingObject.optInt(JSON_OFFSET);
        total = pagingObject.optInt(JSON_TOTAL);
        limit = pagingObject.optInt(JSON_LIMIT);
        next = pagingObject.optString(JSON_NEXT);
        previous = pagingObject.optString(JSON_PREVIOUS);
        href = pagingObject.optString(JSON_HREF);
        items = pagingObject.optJSONArray(JSON_ITEMS);
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return total;
    }

    public JSONArray getItems() {
        return items;
    }

    public String getHref() {
        return href;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }
}
