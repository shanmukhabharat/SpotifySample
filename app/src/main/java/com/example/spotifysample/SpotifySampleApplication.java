package com.example.spotifysample;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Bharath on 06/10/16.
 */
public class SpotifySampleApplication extends Application {

    public static final String TAG = SpotifySampleApplication.class.getSimpleName();

    /**
     * Request queue for volley
     */
    private RequestQueue mRequestQueue;

    /**
     * Application instance
     */
    private static SpotifySampleApplication mApplicationInstance;

    /**
     * Display options for UIL
     */
    public static  DisplayImageOptions mDisplayOptions;

    /**
     * @return  Singleton instance of application class
     */
    public static synchronized SpotifySampleApplication getApplicationInstance(){
        return mApplicationInstance;
    }

    /**
     * @return Display options for UIL
     */
    public static DisplayImageOptions getDisplayOptions(){
        return mDisplayOptions;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //initialising the application instance
        mApplicationInstance = SpotifySampleApplication.this;

        //imageLoader config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(25 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(config);

        mDisplayOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.image_not_available)
                .showImageOnFail(R.drawable.image_not_available)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    /**
     * @return request queue of volley
     */
    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), null);
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param request   request to be added
     * @param tag   set the tag for the request
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    /**
     * Cancels all pending requests using a TAG
     *
     * @param tag   tag to be used
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
