package com.lusifer.shabdkosh.ui.recent;

import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.ui.base.BasePresenter;

import rx.Subscription;

public class RecentPresenter extends BasePresenter<RecentContract.View> implements RecentContract.Presenter {
    private static final String TAG = RecentPresenter.class.getSimpleName();

    private static RecentPresenter instance = null;

    private Subscription mSubscriptions;
    private DataManager mDataManager;

    private RecentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public static RecentPresenter getRecentPresenter(DataManager dataManager) {
        if (instance == null) {
            instance = new RecentPresenter(dataManager);
        }
        return instance;
    }

    @Override
    public void attachView(RecentContract.View mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();

    }

}
