package com.lusifer.shabdkosh.data.local;

import android.content.ContentResolver;
import android.support.annotation.NonNull;

import com.lusifer.shabdkosh.data.model.Result;
import com.lusifer.shabdkosh.data.model.WordDetail;
import com.lusifer.shabdkosh.data.model.local.ModelType;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.provider.ContentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DBHelper {
    public static DBHelper instance = null;

    public static ContentResolver contentResolver = null;

    private DBHelper(ContentResolver contentResolver) {
        DBHelper.contentResolver = contentResolver;
    }

    private DBHelper() {
    }

    public static DBHelper getInstance(ContentResolver contentResolver) {
        if (instance == null || DBHelper.contentResolver == null) {
            instance = new DBHelper(contentResolver);
        }
        return instance;
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    public void saveInRecent(@NonNull WordDetail wordDetail) {
        RecentFavouriteModel recentFavouriteModel = SQLite.select()
                .from(RecentFavouriteModel.class)
                .where(RecentFavouriteModel_Table.wordName.eq(wordDetail.getWord()))
                .and(RecentFavouriteModel_Table.modelType.eq(ModelType.RECENT))
                .querySingle();

        if (recentFavouriteModel == null) {
            recentFavouriteModel = new RecentFavouriteModel();
            recentFavouriteModel.setModelType(ModelType.RECENT);
            recentFavouriteModel.setTimeStamp(System.currentTimeMillis() / 1000L);

            List<Result> resultList = wordDetail.getResults();
            HashMap<String, List<Result>> hashMap = new HashMap<String, List<Result>>();

            for (int i = 0; i < resultList.size(); i++) {
                if (!hashMap.containsKey(resultList.get(i).getPartOfSpeech())) {
                    List<Result> list = new ArrayList<Result>();
                    list.add(resultList.get(i));

                    hashMap.put(resultList.get(i).getPartOfSpeech(), list);
                } else {
                    hashMap.get(resultList.get(i).getPartOfSpeech()).add(resultList.get(i));
                }
            }
            String keySetString = hashMap.keySet().toString();
            recentFavouriteModel.setPartOfSpeech(keySetString.substring(1, keySetString.length()
                    - 1));
            recentFavouriteModel.setWordName(wordDetail.getWord());
            ContentUtils.insert(contentResolver, RecentFavouriteModel.CONTENT_URI,
                    recentFavouriteModel);

        } else {
            recentFavouriteModel.setTimeStamp(System.currentTimeMillis() / 1000L);
            ContentUtils.update(contentResolver, RecentFavouriteModel.CONTENT_URI,
                    recentFavouriteModel);
        }

    }
}
