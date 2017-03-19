package com.lusifer.shabdkosh.ui.favourite;

import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.ui.base.BasePresenter;
import com.lusifer.shabdkosh.utils.RxUtil;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavouritePresenter extends BasePresenter<FavouriteContract.View> implements
        FavouriteContract.Presenter {
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

    @Override
    public void getFavourite() {
        RxUtil.unsubscribe(mSubscriptions);

        mSubscriptions = mDataManager.getFavourite()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<RecentFavouriteModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<RecentFavouriteModel> recentFavouriteModels) {
                        getMvpView().showFavourite(recentFavouriteModels);
                    }
                });
    }
}
