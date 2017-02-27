package com.lusifer.shabdkosh.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtil {

    private static final String TAG = "NetworkUtil";

    public static boolean isNetworkConnected(Context context) {
        Log.i(TAG, "isNetworkConnected: Checking Internet Status");
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}