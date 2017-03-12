package com.lusifer.shabdkosh.ui.detail;

import com.lusifer.shabdkosh.data.model.Word;
import com.lusifer.shabdkosh.ui.base.MvpView;

public interface DetailContract {

    interface View extends MvpView {
        void setWord(Word word);
    }

    interface Presenter {
        void getWord(String word);
    }
}