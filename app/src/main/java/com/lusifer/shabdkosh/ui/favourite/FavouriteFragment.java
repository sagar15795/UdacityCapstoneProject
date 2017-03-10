package com.lusifer.shabdkosh.ui.favourite;

import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.ui.adapter.RecentFavouriteAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteFragment extends Fragment implements FavouriteContract.View {

    @BindView(R.id.rvFavouriteList)
    RecyclerView mRecentRecylerView;

    RecentFavouriteAdapter mRecentFavouriteAdapter;


    FavouritePresenter mFavouritePresenter;

    public static FavouriteFragment getInstance() {
        return new FavouriteFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavouritePresenter = FavouritePresenter.getFavouritePresenter(DataManager.getDataManger());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

        ButterKnife.bind(this, rootView);
        mFavouritePresenter.attachView(this);

        mRecentFavouriteAdapter = new RecentFavouriteAdapter(getTitle(), getPartOfSpeech());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecentRecylerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (mRecentRecylerView.getContext(),
                        layoutManager.getOrientation());
        mRecentRecylerView.addItemDecoration(dividerItemDecoration);
        mRecentRecylerView.setAdapter(mRecentFavouriteAdapter);
        return rootView;
    }

    private List<String> getPartOfSpeech() {
        List<String> title = new ArrayList<>();
        title.add("word1,word2");
        title.add("word2,word2");
        title.add("word3,word2");
        title.add("word4,word2");
        title.add("word5,word2");
        return title;

    }

    private List<String> getTitle() {
        List<String> title = new ArrayList<>();
        title.add("word1");
        title.add("word2");
        title.add("word3");
        title.add("word4");
        title.add("word5");
        return title;
    }

}
