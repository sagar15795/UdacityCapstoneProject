package com.lusifer.shabdkosh.data;


import android.content.ContentResolver;

import com.lusifer.shabdkosh.data.local.DBHelper;
import com.lusifer.shabdkosh.data.model.WordDetail;
import com.lusifer.shabdkosh.data.remote.BaseApiManager;

import rx.Observable;
import rx.functions.Func1;

public class DataManager {

    private static DataManager sInstance = null;
    private BaseApiManager mBaseApiManager;
    private static ContentResolver contentResolver = null;
    private DBHelper dbHelper;

    private DataManager() {
        mBaseApiManager = new BaseApiManager();
        dbHelper = DBHelper.getInstance();
    }

    private DataManager(ContentResolver contentResolver) {
        this();
        dbHelper = DBHelper.getInstance(contentResolver);
    }

    public static DataManager getDataManger() {
        if (sInstance == null) {
            return sInstance = new DataManager();
        } else {
            return sInstance;
        }
    }

    public static DataManager getDataManger(ContentResolver contentResolver) {
        if (sInstance == null || DataManager.contentResolver == null) {
            return sInstance = new DataManager(contentResolver);
        } else {
            return sInstance;
        }
    }


    public Observable<WordDetail> getWord(String word) {
        return mBaseApiManager.getWordApi().getWord(word)
                .concatMap(new Func1<WordDetail, Observable<? extends WordDetail>>() {
                    @Override
                    public Observable<? extends WordDetail> call(WordDetail wordDetail) {
                        dbHelper.saveInRecent(wordDetail);
                        return Observable.just(wordDetail);
                    }
                });
    }


}
