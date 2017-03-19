package com.lusifer.shabdkosh.ui.favourite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.ui.adapter.RecentFavouriteAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteFragment extends Fragment implements FavouriteContract.View {

    @BindView(R.id.rvFavouriteList)
    RecyclerView mRecentRecylerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.tvNoFavorite)
    TextView tvNoRecentMsg;


    RecentFavouriteAdapter mRecentFavouriteAdapter;


    FavouritePresenter mFavouritePresenter;
    private List<RecentFavouriteModel> recentFavouriteModels;

    public static FavouriteFragment getInstance() {
        return new FavouriteFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavouritePresenter = FavouritePresenter.getFavouritePresenter(DataManager.getDataManger
                (getActivity().getContentResolver()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

        ButterKnife.bind(this, rootView);
        mFavouritePresenter.attachView(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecentRecylerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecentRecylerView.getContext(), layoutManager.getOrientation());
        mRecentRecylerView.addItemDecoration(dividerItemDecoration);
        mFavouritePresenter.getFavourite();
        return rootView;
    }

    private List<String> getPartOfSpeech(List<RecentFavouriteModel> recentFavouriteModels) {
        List<String> title = new ArrayList<>();
        for (int i = 0; i < recentFavouriteModels.size(); i++) {
            title.add(recentFavouriteModels.get(i).getPartOfSpeech());
        }
        return title;

    }

    private List<String> getTitle(List<RecentFavouriteModel> recentFavouriteModels) {

        List<String> title = new ArrayList<>();
        for (int i = 0; i < recentFavouriteModels.size(); i++) {
            title.add(recentFavouriteModels.get(i).getWordName());
        }
        return title;
    }

    @Override
    public void showFavourite(List<RecentFavouriteModel> recentFavouriteModels) {
        this.recentFavouriteModels = recentFavouriteModels;
        mRecentFavouriteAdapter = new RecentFavouriteAdapter(getTitle(recentFavouriteModels),
                getPartOfSpeech(recentFavouriteModels));
        mRecentRecylerView.setAdapter(mRecentFavouriteAdapter);
        progressBar.setVisibility(View.GONE);
        if (this.recentFavouriteModels.isEmpty()) {
            tvNoRecentMsg.setVisibility(View.VISIBLE);
        } else {
            mRecentRecylerView.setVisibility(View.VISIBLE);
        }
    }
}
