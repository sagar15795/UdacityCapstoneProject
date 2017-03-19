package com.lusifer.shabdkosh.ui.main;


import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.search.SearchResult;
import com.lusifer.shabdkosh.ui.base.BasePresenter;
import com.lusifer.shabdkosh.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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


    @Override
    public void getSearchResultDB(final String query) {
        RxUtil.unsubscribe(mSubscriptions);
        getMvpView().showSearchViewProgress();
        mSubscriptions = mDataManager.getSearchHistoryFromDB(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<RecentFavouriteModel>>() {
                    @Override
                    public void onCompleted() {
                        getSearchResult(query);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<RecentFavouriteModel> searchResult) {
                        getMvpView().swapSuggestionsFromDB(searchResult);
                    }
                });
    }


    @Override
    public void getSearchResult(String query) {
        RxUtil.unsubscribe(mSubscriptions);
        mSubscriptions = mDataManager.getSearchHistory(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().hideSearchViewProgress();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        getMvpView().swapSuggestions(getSuggestion(searchResult));
                    }
                });
    }

    @Override
    public void unSubscribe(){
        RxUtil.unsubscribe(mSubscriptions);
    }

    private List<RecentFavouriteModel> getSuggestion(SearchResult searchResult) {
        List<RecentFavouriteModel> recentFavouriteModels = new ArrayList<>();

        for (String s : searchResult.getResults().getData()) {
            RecentFavouriteModel recentFavouriteModel = new RecentFavouriteModel(s);
            recentFavouriteModels.add(recentFavouriteModel);
        }
        return recentFavouriteModels;
    }
}
