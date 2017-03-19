package com.lusifer.shabdkosh.data.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lusifer.shabdkosh.data.model.local.ModelType;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel_Table;
import com.lusifer.shabdkosh.data.model.word.Result;
import com.lusifer.shabdkosh.data.model.word.WordDetail;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.provider.ContentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;


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


    public Observable<List<RecentFavouriteModel>> getRecent(final String query) {
        return Observable.defer(new Func0<Observable<List<RecentFavouriteModel>>>() {
            @Override
            public Observable<List<RecentFavouriteModel>> call() {
                Cursor cursor = ContentUtils.query(contentResolver,
                        RecentFavouriteModel.CONTENT_URI, ConditionGroup.clause(
                                RecentFavouriteModel_Table.modelType.eq(ModelType.RECENT)), null,
                        null);

                List<RecentFavouriteModel> searchResults = new ArrayList<RecentFavouriteModel>();
                if (!TextUtils.isEmpty(query)) {
                    if (cursor != null) {
                        cursor.moveToFirst();
                        do {
                            String data = cursor.getString(cursor.getColumnIndex("wordName"));
                            if (data.toLowerCase().contains(query.toLowerCase())) {
                                RecentFavouriteModel recentFavouriteModel = new
                                        RecentFavouriteModel(data);
                                recentFavouriteModel.setmIsHistory(false);
                                searchResults.add(recentFavouriteModel);
                            }

                        } while (cursor.moveToNext());
                    }

                    return Observable.just(searchResults);
                } else {
                    return Observable.empty();
                }
            }
        });
    }


    public Observable<List<RecentFavouriteModel>> getRecent() {
        return Observable.defer(new Func0<Observable<List<RecentFavouriteModel>>>() {
            @Override
            public Observable<List<RecentFavouriteModel>> call() {

                List<RecentFavouriteModel> searchResults = SQLite.select()
                        .from(RecentFavouriteModel.class)
                        .where(RecentFavouriteModel_Table.modelType.eq(ModelType.RECENT))
                        .queryList();

                if (searchResults != null && !searchResults.isEmpty()) {

                    return Observable.just(searchResults);
                } else {
                    return Observable.empty();
                }
            }
        });
    }

    public Observable<List<RecentFavouriteModel>> getFavourite() {
        return Observable.defer(new Func0<Observable<List<RecentFavouriteModel>>>() {
            @Override
            public Observable<List<RecentFavouriteModel>> call() {

                List<RecentFavouriteModel> searchResults = SQLite.select()
                        .from(RecentFavouriteModel.class)
                        .where(RecentFavouriteModel_Table.modelType.eq(ModelType.FAVOURITE))
                        .queryList();

                if (searchResults != null && !searchResults.isEmpty()) {

                    return Observable.just(searchResults);
                } else {
                    return Observable.empty();
                }
            }
        });
    }
}
