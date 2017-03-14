package com.lusifer.shabdkosh.data;


import com.lusifer.shabdkosh.data.model.WordDetail;
import com.lusifer.shabdkosh.data.remote.BaseApiManager;

import rx.Observable;

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


    public Observable<WordDetail> getWord(String word) {
        return mBaseApiManager.getWordApi().getWord(word);
    }

}
