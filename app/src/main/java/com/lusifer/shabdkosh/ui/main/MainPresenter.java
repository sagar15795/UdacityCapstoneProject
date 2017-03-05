package com.lusifer.shabdkosh.ui.main;


import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.ui.base.BasePresenter;

import rx.Subscription;

class MainPresenter extends BasePresenter<MainContracts.View> implements MainContracts.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private static MainPresenter instance = null;

    private Subscription mSubscriptions;
    private DataManager mDataManager;

    private MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public static MainPresenter getMainPresenter(DataManager dataManager) {
        if (instance == null) {
            instance = new MainPresenter(dataManager);
        }
        return instance;
    }

    @Override
    public void attachView(MainContracts.View mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();

    }


}
