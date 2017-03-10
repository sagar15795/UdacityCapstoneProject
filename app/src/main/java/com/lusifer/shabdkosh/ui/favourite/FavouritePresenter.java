package com.lusifer.shabdkosh.ui.favourite;

import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.ui.base.BasePresenter;

import rx.Subscription;

public class FavouritePresenter extends BasePresenter<FavouriteContract.View> implements FavouriteContract.Presenter {
    private static final String TAG = FavouritePresenter.class.getSimpleName();

    private static FavouritePresenter instance = null;

    private Subscription mSubscriptions;
    private DataManager mDataManager;

    private FavouritePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public static FavouritePresenter getFavouritePresenter(DataManager dataManager) {
        if (instance == null) {
            instance = new FavouritePresenter(dataManager);
        }
        return instance;
    }

    @Override
    public void attachView(FavouriteContract.View mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();

    }

}
