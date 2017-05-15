package com.lusifer.shabdkosh.ui.main;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.ui.adapter.ViewPagerAdapter;
import com.lusifer.shabdkosh.ui.detail.DetailActivity;
import com.lusifer.shabdkosh.ui.favourite.FavouriteFragment;
import com.lusifer.shabdkosh.ui.recent.RecentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContracts.View,
        FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener,
        SearchSuggestionsAdapter.OnBindSuggestionCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.floating_search_view)
    FloatingSearchView mFloatingSearchView;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    private MainPresenter mMainPresenter;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<RecentFavouriteModel> recentFavouriteModelsSearch;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mMainPresenter = MainPresenter.getMainPresenter(DataManager.getDataManger
                (getContentResolver()));
        mMainPresenter.attachView(this);

        recentFavouriteModelsSearch = new ArrayList<>();

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewPagerAdapter.addFragment(RecentFragment.getInstance(), getString(R.string.tab_recent));
        mViewPagerAdapter.addFragment(FavouriteFragment.getInstance(), getString(R.string.tab_favourite));

        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mFloatingSearchView.setOnQueryChangeListener(this);
        mFloatingSearchView.setOnSearchListener(this);
        mFloatingSearchView.setOnBindSuggestionCallback(this);
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        if (!oldQuery.equals("") && newQuery.equals("")) {
            mFloatingSearchView.clearSuggestions();
            mMainPresenter.unSubscribe();
            hideSearchViewProgress();
        } else {
            mMainPresenter.getSearchResultDB(newQuery);

            Bundle bundle =new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Search Word");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, newQuery);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }
    }


    @Override
    public void showSearchViewProgress() {
        mFloatingSearchView.showProgress();
    }

    @Override
    public void hideSearchViewProgress() {
        mFloatingSearchView.hideProgress();
    }

    @Override
    public void swapSuggestions(List<RecentFavouriteModel> recentFavouriteModels) {
        recentFavouriteModelsSearch.addAll(recentFavouriteModels);
        mFloatingSearchView.swapSuggestions(recentFavouriteModelsSearch);
    }

    @Override
    public void swapSuggestionsFromDB(List<RecentFavouriteModel> recentFavouriteModels) {
        recentFavouriteModelsSearch = new ArrayList<>();
        recentFavouriteModelsSearch.addAll(recentFavouriteModels);
        mFloatingSearchView.swapSuggestions(recentFavouriteModelsSearch);
    }


    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.WORD_EXTRA, searchSuggestion.getBody());
        startActivity(intent);
    }

    @Override
    public void onSearchAction(String currentQuery) {
        Intent intent = new Intent(this, DetailActivity.class);
        if(!recentFavouriteModelsSearch.isEmpty()) {
            intent.putExtra(DetailActivity.WORD_EXTRA, recentFavouriteModelsSearch.get(
                    recentFavouriteModelsSearch.size() - 1).getBody());
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.error_text_not_load, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView,
                                 SearchSuggestion item, int itemPosition) {
        RecentFavouriteModel colorSuggestion = (RecentFavouriteModel) item;

        String textColor = "#000000";
        String textLight = "#787878";

        if (!colorSuggestion.ismIsHistory()) {
            leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_history_black_24dp, null));

            Util.setIconColor(leftIcon, Color.parseColor(textColor));
            leftIcon.setAlpha(.36f);
        } else {
            leftIcon.setAlpha(0.0f);
            leftIcon.setImageDrawable(null);
        }

        textView.setTextColor(Color.parseColor(textColor));
        String text = colorSuggestion.getBody()
                .replaceFirst(mFloatingSearchView.getQuery(),
                        "<font color=\"" + textLight + "\">" + mFloatingSearchView.getQuery() +
                                "</font>");
        textView.setText(Html.fromHtml(text));
    }
}
