package com.lusifer.shabdkosh.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.model.Result;
import com.lusifer.shabdkosh.data.model.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment implements DetailContract.View {

    @BindView(R.id.tvWord)
    TextView tvWord;

    @BindView(R.id.tvPronounce)
    TextView tvPronounce;

    @BindView(R.id.vDetailPartOfSpeech)
    LinearLayout vDetailPartOfSpeech;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TextView tvPartOfSpeech;
    LinearLayout vContentDetailPartOfSpeechData;
    TextView tvId;
    TextView tvWordDescription;
    FlexboxLayout flexboxLayoutSynonyms;
    View vDividerDescription;


    DetailPresenter mDetailPresenter;
    LayoutInflater inflater;


    public static DetailFragment newInstance() {

        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailPresenter = DetailPresenter.getDetailPresenter(DataManager.getDataManger());
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

        mDetailPresenter.getWord("wait");
        toolbar.setTitle("HELLO");
        ((DetailActivity) getActivity()).setSupportActionBar(toolbar);

        if (((DetailActivity) getActivity()).getSupportActionBar() != null) {
            ((DetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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

                    Spannable word = new SpannableString(synonyms.get(i - 1));

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
    public void setWord(Word word) {

        HashMap<String, List<Result>> result = getGroupedResult(word.getResults());
        String string = "";
        for (int i = 0; i < word.getSyllables().getList().size(); i++) {
            if (i != word.getSyllables().getList().size() - 1) {
                string += word.getSyllables().getList().get(i) + "\u00B7";
            } else {
                string += word.getSyllables().getList().get(i);
            }
        }
        tvWord.setText(string);
        tvPronounce.setText(String.format("/%s/", word.getPronunciation().getAll()));
        List<String> l = new ArrayList<String>(result.keySet());

        for (int i = 0; i < l.size(); i = i + 2) {
            vDetailPartOfSpeech.addView(getContentDetailPartOfSpeech(result.get(l.get(i))));
            vDetailPartOfSpeech.addView(getContentDetailPartOfSpeech(result.get(l.get(i + 1))));
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
        return hashMap;
    }

}
