package com.lusifer.shabdkosh.ui.recent;

import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.ui.base.MvpView;

import java.util.List;

public interface RecentContract {

    interface View extends MvpView {
        void showRecent(List<RecentFavouriteModel> recentFavouriteModels);
    }

    interface Presenter {
        void getRecent();
    }
}