package com.lusifer.shabdkosh.ui.main;


import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.word.WordDetail;
import com.lusifer.shabdkosh.ui.base.MvpView;
import com.lusifer.shabdkosh.utils.RxUtil;

import java.util.List;

interface MainContracts {

    interface View extends MvpView {

        void showSearchViewProgress();

        void hideSearchViewProgress();

        void swapSuggestions(List<RecentFavouriteModel> recentFavouriteModels);

        void swapSuggestionsFromDB(List<RecentFavouriteModel> recentFavouriteModels);
    }

    interface Presenter {

        void getSearchResult(String query);

        void getSearchResultDB(String query);

        void unSubscribe();

    }
}
