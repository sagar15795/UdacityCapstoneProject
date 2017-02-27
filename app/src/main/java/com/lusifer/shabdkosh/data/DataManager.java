package com.lusifer.shabdkosh.data;


import com.lusifer.shabdkosh.data.remote.BaseApiManager;

public class DataManager {

    private static DataManager sInstance = null;
    private BaseApiManager mBaseApiManager;


    private DataManager() {
        this.mBaseApiManager = new BaseApiManager();
    }

    public static DataManager getDataManger() {
        if (sInstance == null) {
            return sInstance = new DataManager();
        } else {
            return sInstance;
        }
    }

}
