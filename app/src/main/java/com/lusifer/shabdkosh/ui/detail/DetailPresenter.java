package com.lusifer.shabdkosh.ui.detail;

import android.util.Log;

import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.word.WordDetail;
import com.lusifer.shabdkosh.ui.base.BasePresenter;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract
        .Presenter {
    private static final String TAG = DetailPresenter.class.getSimpleName();

    private static DetailPresenter instance = null;

    private Subscription mSubscriptions;
    private DataManager mDataManager;

    private DetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public static DetailPresenter getDetailPresenter(DataManager dataManager) {
        if (instance == null) {
            instance = new DetailPresenter(dataManager);
        }
        return instance;
    }

    @Override
    public void attachView(DetailContract.View mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();

    }

    @Override
    public void getWord(final String word) {
        mSubscriptions = mDataManager.getWord(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WordDetail>() {
                    @Override
                    public void onCompleted() {
                        isFavourite(word);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        getMvpView().error();
                    }

                    @Override
                    public void onNext(WordDetail wordDetail) {
                        getMvpView().setWord(wordDetail);
                    }
                });
    }

    @Override
    public void saveWord(RecentFavouriteModel recentFavouriteModel) {
        mSubscriptions = mDataManager.toggleFavourite(recentFavouriteModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().favError();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        getMvpView().setIcon(aBoolean);
                    }
                });
    }

    private void isFavourite(String word) {
        mSubscriptions = mDataManager.isFavourite(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().favError();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        getMvpView().setIcon(aBoolean);
                    }
                });
    }

}
