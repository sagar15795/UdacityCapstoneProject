package com.lusifer.shabdkosh.ui.detail;

import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.word.WordDetail;
import com.lusifer.shabdkosh.ui.base.MvpView;

public interface DetailContract {

    interface View extends MvpView {
        void setWord(WordDetail wordDetail);

        void error();

        void favError();

        void setIcon(Boolean aBoolean);
    }

    interface Presenter {
        void getWord(String word);

        void saveWord(RecentFavouriteModel recentFavouriteModel);
    }
}