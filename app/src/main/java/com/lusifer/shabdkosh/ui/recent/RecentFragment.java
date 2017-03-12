package com.lusifer.shabdkosh.ui.recent;

import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.ui.adapter.RecentFavouriteAdapter;
import com.lusifer.shabdkosh.ui.detail.DetailActivity;
import com.lusifer.shabdkosh.utils.RecyclerItemClickListner;

import android.content.Intent;
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

public class RecentFragment extends Fragment implements RecentContract.View, RecyclerItemClickListner.OnItemClickListener {

    @BindView(R.id.rvRecentList)
    RecyclerView mRecentRecylerView;

    RecentPresenter mRecentPresenter;

    RecentFavouriteAdapter mRecentFavouriteAdapter;

    public static RecentFragment getInstance() {
        return new RecentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecentPresenter = RecentPresenter.getRecentPresenter(DataManager.getDataManger());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recent, container, false);
        ButterKnife.bind(this, rootView);
        mRecentPresenter.attachView(this);

        mRecentFavouriteAdapter = new RecentFavouriteAdapter(getTitle(), getPartOfSpeech());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecentRecylerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (mRecentRecylerView.getContext(),
                        layoutManager.getOrientation());
        mRecentRecylerView.addItemDecoration(dividerItemDecoration);
        mRecentRecylerView.addOnItemTouchListener(new RecyclerItemClickListner(getActivity(),this));
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


    @Override
    public void onItemClick(View childView, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }


}
