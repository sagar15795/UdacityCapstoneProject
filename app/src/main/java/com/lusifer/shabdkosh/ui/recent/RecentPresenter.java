package com.lusifer.shabdkosh.ui.recent;

import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.ui.base.BasePresenter;
import com.lusifer.shabdkosh.utils.RxUtil;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecentPresenter extends BasePresenter<RecentContract.View> implements RecentContract
        .Presenter {
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


    @Override
    public void getRecent() {
        RxUtil.unsubscribe(mSubscriptions);

        mSubscriptions = mDataManager.getRecent()
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
                        getMvpView().showRecent(recentFavouriteModels);
                    }
                });
    }
}
