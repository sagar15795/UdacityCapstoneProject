package com.lusifer.shabdkosh.ui.recent;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.local.ModelType;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel_Table;
import com.lusifer.shabdkosh.ui.adapter.RecentFavouriteAdapter;
import com.lusifer.shabdkosh.ui.detail.DetailActivity;
import com.lusifer.shabdkosh.utils.RecyclerItemClickListner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentFragment extends Fragment implements RecentContract.View,
        RecyclerItemClickListner.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = RecentFragment.class.getSimpleName();
    @BindView(R.id.rvRecentList)
    RecyclerView mRecentRecylerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.tvNoRecent)
    TextView tvNoRecentMsg;

    RecentPresenter mRecentPresenter;

    RecentFavouriteAdapter mRecentFavouriteAdapter;
    List<RecentFavouriteModel> recentFavouriteModels;

    public static RecentFragment getInstance() {
        return new RecentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecentPresenter = RecentPresenter.getRecentPresenter(DataManager.getDataManger(
                getActivity().getContentResolver()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recent, container, false);
        ButterKnife.bind(this, rootView);
        mRecentPresenter.attachView(this);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecentRecylerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecentRecylerView.getContext(), layoutManager.getOrientation());
        mRecentRecylerView.addItemDecoration(dividerItemDecoration);
        mRecentRecylerView.addOnItemTouchListener(new RecyclerItemClickListner(getActivity(),
                this));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        mRecentPresenter.getRecent();
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
    public void onItemClick(View childView, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.WORD_EXTRA, recentFavouriteModels.get(position)
                .getWordName());
        startActivity(intent);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }


    @Override
    public void showRecent(List<RecentFavouriteModel> recentFavouriteModels) {
        this.recentFavouriteModels = recentFavouriteModels;
        mRecentFavouriteAdapter = new RecentFavouriteAdapter(getTitle(recentFavouriteModels),
                getPartOfSpeech(recentFavouriteModels));
        mRecentRecylerView.setAdapter(mRecentFavouriteAdapter);
        progressBar.setVisibility(View.GONE);
        if (this.recentFavouriteModels.isEmpty()) {
            tvNoRecentMsg.setVisibility(View.VISIBLE);
        } else {
            mRecentRecylerView.setVisibility(View.VISIBLE);
            tvNoRecentMsg.setVisibility(View.GONE);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(getContext(), RecentFavouriteModel.CONTENT_URI,
                null, RecentFavouriteModel_Table.modelType + "=?", new String[]{String.valueOf
                (ModelType.RECENT)}, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished: " + cursor.getCount());

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.d(TAG, "onLoadFinished: " + cursor.getString(cursor.getColumnIndex("wordName")));
            Log.d(TAG, "onLoadFinished: " + cursor.getString(
                    cursor.getColumnIndex("partOfSpeech")));

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
