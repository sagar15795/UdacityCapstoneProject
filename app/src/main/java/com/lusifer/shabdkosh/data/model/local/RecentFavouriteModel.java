package com.lusifer.shabdkosh.data.model.local;

import android.net.Uri;

import com.lusifer.shabdkosh.data.local.ShabdkoshDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;
import com.raizlabs.android.dbflow.structure.provider.BaseProviderModel;

@TableEndpoint(name = RecentFavouriteModel.ENDPOINT, contentProvider = ShabdkoshDatabase.class)
@Table(database = ShabdkoshDatabase.class, name = RecentFavouriteModel.ENDPOINT)
public class RecentFavouriteModel extends BaseProviderModel {

    public static final String ENDPOINT = "RecentFavouriteModel";

    @ContentUri(path = ENDPOINT, type = ContentUri.ContentType.VND_MULTIPLE + ENDPOINT)
    public static Uri CONTENT_URI = buildUri(ENDPOINT);

    @PrimaryKey(autoincrement = true)
    Long id;

    @Column
    String wordName;

    @Column
    String partOfSpeech;

    @Column
    Long timeStamp;

    @Column
    ModelType modelType;

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = Uri.parse(ShabdkoshDatabase.BASE_CONTENT_URI + ShabdkoshDatabase
                .AUTHORITY).buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @Override
    public Uri getDeleteUri() {
        return RecentFavouriteModel.CONTENT_URI;
    }

    @Override
    public Uri getInsertUri() {
        return RecentFavouriteModel.CONTENT_URI;
    }

    @Override
    public Uri getUpdateUri() {
        return RecentFavouriteModel.CONTENT_URI;
    }

    @Override
    public Uri getQueryUri() {
        return RecentFavouriteModel.CONTENT_URI;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }
}
