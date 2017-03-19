package com.lusifer.shabdkosh.data.model.local;

import android.net.Uri;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.lusifer.shabdkosh.data.local.ShabdkoshDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;
import com.raizlabs.android.dbflow.structure.provider.BaseProviderModel;

@TableEndpoint(name = RecentFavouriteModel.ENDPOINT, contentProvider = ShabdkoshDatabase.class)
@Table(database = ShabdkoshDatabase.class, name = RecentFavouriteModel.ENDPOINT)
public class RecentFavouriteModel extends BaseProviderModel implements SearchSuggestion {

    public static final String ENDPOINT = "RecentFavouriteModel";

    public static final Creator<RecentFavouriteModel> CREATOR = new Creator<RecentFavouriteModel>
            () {
        @Override
        public RecentFavouriteModel createFromParcel(Parcel in) {
            return new RecentFavouriteModel(in);
        }

        @Override
        public RecentFavouriteModel[] newArray(int size) {
            return new RecentFavouriteModel[size];
        }
    };

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

    @ColumnIgnore
    private boolean mIsHistory = false;

    public RecentFavouriteModel(Parcel source) {
        this.wordName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public RecentFavouriteModel(String wordName) {
        this.wordName = wordName;
        this.mIsHistory = true;
    }

    public RecentFavouriteModel() {

    }

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

    @Override
    public String getBody() {
        return wordName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wordName);
        parcel.writeInt(mIsHistory ? 1 : 0);
    }

    public boolean ismIsHistory() {
        return mIsHistory;
    }

    public void setmIsHistory(boolean mIsHistory) {
        this.mIsHistory = mIsHistory;
    }
}
