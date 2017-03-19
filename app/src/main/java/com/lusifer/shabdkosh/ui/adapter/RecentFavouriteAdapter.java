package com.lusifer.shabdkosh.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lusifer.shabdkosh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecentFavouriteAdapter extends RecyclerView.Adapter<RecentFavouriteAdapter
        .ViewHolder> {

    private List<String> mTitleList, mPartOfSpeechList;

    public RecentFavouriteAdapter() {
        mTitleList = new ArrayList<>();
        mPartOfSpeechList = new ArrayList<>();
    }

    public RecentFavouriteAdapter(List<String> titleList, List<String> partOfSpeechList) {
        mTitleList = titleList;
        mPartOfSpeechList = partOfSpeechList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recent_and_favourite_recycler_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null) {
            holder.tvWordTitle.setText(mTitleList.get(position));
            holder.tvWordTitle.setContentDescription(mTitleList.get(position));
            holder.tvPartOfSpeech.setText(mPartOfSpeechList.get(position));
            holder.tvPartOfSpeech.setContentDescription(mPartOfSpeechList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return Math.min(mTitleList.size(), mPartOfSpeechList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvWord)
        TextView tvWordTitle;

        @BindView(R.id.tvPartOfSpeech)
        TextView tvPartOfSpeech;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
