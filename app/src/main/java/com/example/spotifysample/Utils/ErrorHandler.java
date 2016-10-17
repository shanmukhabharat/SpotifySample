package com.example.spotifysample.Utils;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.example.spotifysample.R;

/**
 * Created by Bharath on 08/10/16.
 */

public class ErrorHandler {

    /**
     * Returns the message based on error
     * @param error error that has to be considered
     * @param context   activity context
     * @return  a message explaining the cause of the error
     */
    public static String getMessage(Object error, Context context) {

        //handling timeout error
        if (isTimeoutError(error)) {
            return context.getResources().getString(R.string.error_timeout);
        }
        //handling server error
        else if (isServerProblem(error)) {
            return context.getResources().getString(R.string.error_generic);
        }
        //handling error if there is no internet connection
        else if (isNoConnectionProblem(error)){
            return context.getResources().getString(R.string.error_no_connection);
        }
        //handling network error
        else if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.error_network);
        }

        return context.getResources().getString(R.string.error_generic);
    }

    /**
     * @param error error that has to be considered
     * @return a boolean indicating if error is a no connection error
     */
    public static boolean isNoConnectionProblem(Object error) {
        return error instanceof NoConnectionError;
    }

    /**
     * @param error that has to be considered
     * @return a boolean indicating if error is a network error
     */
    public static boolean isNetworkProblem(Object error){
        return error instanceof NetworkError;
    }

    /**
     * @param error error that has to be considered
     * @return  a boolean indicating if error is a server error
     */
    public static boolean isServerProblem(Object error) {
        return error instanceof ServerError;
    }

    /**
     * @param error error that has to be considered
     * @return  a boolean indicating if error is a timeout error
     */
    public static boolean isTimeoutError(Object error){
        return error instanceof TimeoutError;
    }

}