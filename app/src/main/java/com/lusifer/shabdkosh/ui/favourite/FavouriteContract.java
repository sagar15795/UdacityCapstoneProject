package com.lusifer.shabdkosh.ui.favourite;

import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.ui.base.MvpView;

import java.util.List;

public interface FavouriteContract {

    interface View extends MvpView {
        void showFavourite(List<RecentFavouriteModel> recentFavouriteModels);
    }

    interface Presenter {
        void getFavourite();
    }
}