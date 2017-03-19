package com.lusifer.shabdkosh.ui.detail;

import com.lusifer.shabdkosh.data.model.word.WordDetail;
import com.lusifer.shabdkosh.ui.base.MvpView;

public interface DetailContract {

    interface View extends MvpView {
        void setWord(WordDetail wordDetail);
    }

    interface Presenter {
        void getWord(String word);
    }
}