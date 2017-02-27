package com.lusifer.shabdkosh.data;


import com.lusifer.shabdkosh.data.remote.BaseApiManager;

public class DataManager {

    private static DataManager INSTANCE = null;
    private BaseApiManager mBaseApiManager;


    private DataManager() {
        this.mBaseApiManager = new BaseApiManager();
    }

    public static DataManager getDataManger() {
        if (INSTANCE == null) {
            return INSTANCE = new DataManager();
        } else {
            return INSTANCE;
        }
    }

}
