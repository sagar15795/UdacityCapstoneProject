package com.lusifer.shabdkosh.ui.detail;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.local.ModelType;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.word.Result;
import com.lusifer.shabdkosh.data.model.word.WordDetail;
import com.lusifer.shabdkosh.ui.widget.WidgetProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment implements DetailContract.View, View.OnClickListener {

    @BindView(R.id.tvWord)
    TextView tvWord;

    @BindView(R.id.tvPronounce)
    TextView tvPronounce;

    @BindView(R.id.vDetailPartOfSpeech)
    LinearLayout vDetailPartOfSpeech;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    LinearLayout linearLayout;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.tvErrorMsg)
    TextView tvErrorMsg;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    TextView tvPartOfSpeech;
    LinearLayout vContentDetailPartOfSpeechData;
    TextView tvId;
    TextView tvWordDescription;
    FlexboxLayout flexboxLayoutSynonyms;
    View vDividerDescription;


    DetailPresenter mDetailPresenter;
    LayoutInflater inflater;

    private static final String ARGS_WORD = "args_word";
    private String word;
    private String partOfSpeech;

    public static DetailFragment newInstance(String word) {

        Bundle args = new Bundle();
        args.putString(ARGS_WORD, word);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            word = getArguments().getString(ARGS_WORD);
        } else {
            getActivity().finish();
        }
        mDetailPresenter = DetailPresenter.getDetailPresenter(DataManager.getDataManger(
                getActivity().getContentResolver()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        mDetailPresenter.attachView(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDetailPresenter.getWord(word);
        toolbar.setTitle(word);
        ((DetailActivity) getActivity()).setSupportActionBar(toolbar);

        if (((DetailActivity) getActivity()).getSupportActionBar() != null) {
            ((DetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        floatingActionButton.setOnClickListener(this);
    }


    private View getContentDetailPartOfSpeechData(int id, String description, List<String>
            synonyms, boolean dividerFlag) {
        View contentDetailPartOfSpeechData = inflater.inflate(
                R.layout.content_detail_partofspeech_data, null);
        RelativeLayout container = ButterKnife.findById(contentDetailPartOfSpeechData, R.id
                .detail_data_partofspeech);
        tvId = ButterKnife.findById(contentDetailPartOfSpeechData, R.id.tvId);
        tvId.setText(String.format(getString(R.string.string_id), id));

        tvWordDescription = ButterKnife.findById(contentDetailPartOfSpeechData, R.id
                .tvWordDescription);
        tvWordDescription.setText(description);

        flexboxLayoutSynonyms = ButterKnife.findById(contentDetailPartOfSpeechData, R.id
                .flSynonyms);
        if (synonyms != null) {
            for (int i = 0; i <= synonyms.size(); i++) {
                TextView textView = new TextView(getContext());
                if (i != 0) {

                    flexboxLayoutSynonyms.addView(textView);

                    final Spannable word = new SpannableString(synonyms.get(i - 1));

                    word.setSpan(new ForegroundColorSpan(Color.BLUE), 0, word.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(word);

                    if (i != synonyms.size()) {
                        Spannable wordTwo = new SpannableString(",");
                        textView.append(wordTwo);
                    } else {
                        Spannable wordTwo = new SpannableString(".");
                        textView.append(wordTwo);
                    }
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra(DetailActivity.WORD_EXTRA, word.toString());
                            ((DetailActivity) getActivity()).startActivity(intent);
                        }
                    });
                } else {
                    textView.setText(R.string.string_synonyms);
                    flexboxLayoutSynonyms.addView(textView);
                }

            }
        } else {
            container.removeView(flexboxLayoutSynonyms);
        }

        vDividerDescription = ButterKnife.findById(contentDetailPartOfSpeechData,
                R.id.vDividerDescription);
        if (dividerFlag) {
            container.removeView(vDividerDescription);
        }
        return contentDetailPartOfSpeechData;

    }

    private View getContentDetailPartOfSpeech(List<Result> results) {
        View contentDetailPartOfSpeech = inflater.inflate(R.layout.content_detail_partofspeech,
                null);
        tvPartOfSpeech = ButterKnife.findById(contentDetailPartOfSpeech, R.id.tvPartOfSpeech);
        tvPartOfSpeech.setText(results.get(0).getPartOfSpeech());
        vContentDetailPartOfSpeechData = ButterKnife.findById(contentDetailPartOfSpeech,
                R.id.vContentDetailPartOfSpeechData);

        for (int i = 0; i < results.size(); i++) {
            if (i != results.size() - 1) {
                vContentDetailPartOfSpeechData.addView(getContentDetailPartOfSpeechData(i + 1,
                        results.get(i).getDefinition(), results.get(i).getSynonyms(), false));
            } else {
                vContentDetailPartOfSpeechData.addView(getContentDetailPartOfSpeechData(i + 1,
                        results.get(i).getDefinition(), results.get(i).getSynonyms(), true));
            }
        }

        return contentDetailPartOfSpeech;

    }


    @Override
    public void setWord(WordDetail wordDetail) {

        HashMap<String, List<Result>> result = getGroupedResult(wordDetail.getResults());
        String string = "";
        if (wordDetail.getSyllables() != null) {
            for (int i = 0; i < wordDetail.getSyllables().getList().size(); i++) {
                if (i != wordDetail.getSyllables().getList().size() - 1) {
                    string += wordDetail.getSyllables().getList().get(i) + "\u00B7";
                } else {
                    string += wordDetail.getSyllables().getList().get(i);
                }
            }
            tvWord.setText(string);
        } else {
            tvWord.setText(word);
        }
        if (wordDetail.getPronunciation() != null) {
            tvPronounce.setText(String.format("/%s/", wordDetail.getPronunciation().getAll()));
        } else {
            tvPronounce.setVisibility(View.GONE);
        }

        List<String> l = new ArrayList<String>(result.keySet());

        for (int i = 0; i < l.size(); i++) {
            vDetailPartOfSpeech.addView(getContentDetailPartOfSpeech(result.get(l.get(i))));
        }


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(getContext(), WidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listview);
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void error() {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        tvErrorMsg.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        }, 500);
    }

    @Override
    public void favError() {
        Snackbar.make(linearLayout, getString(R.string.something_went_wrong), Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setIcon(Boolean aBoolean) {
        floatingActionButton.setVisibility(View.VISIBLE);
        if (!aBoolean) {
            floatingActionButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }


    private HashMap<String, List<Result>> getGroupedResult(List<Result> resultList) {
        HashMap<String, List<Result>> hashMap = new HashMap<String, List<Result>>();
        for (int i = 0; i < resultList.size(); i++) {
            if (!hashMap.containsKey(resultList.get(i).getPartOfSpeech())) {
                List<Result> list = new ArrayList<Result>();
                list.add(resultList.get(i));

                hashMap.put(resultList.get(i).getPartOfSpeech(), list);
            } else {
                hashMap.get(resultList.get(i).getPartOfSpeech()).add(resultList.get(i));
            }
        }
        partOfSpeech = hashMap.keySet().toString().substring(1, hashMap.keySet().toString().length()
                - 1);

        return hashMap;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mDetailPresenter.detachView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                RecentFavouriteModel favouriteModel = new RecentFavouriteModel(word);
                favouriteModel.setPartOfSpeech(partOfSpeech);
                favouriteModel.setModelType(ModelType.FAVOURITE);
                mDetailPresenter.saveWord(favouriteModel);
                break;
        }
    }
}
