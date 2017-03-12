package com.lusifer.shabdkosh.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Word {

    @SerializedName("frequency")
    private Double frequency;
    @SerializedName("pronunciation")
    private Pronunciation pronunciation;
    @SerializedName("results")
    private List<Result> results;
    @SerializedName("syllables")
    private Syllables syllables;
    @SerializedName("word")
    private String word;

    public Pronunciation getPronunciation() {
        return pronunciation;
    }

    public List<Result> getResults() {
        return results;
    }

    public Syllables getSyllables() {
        return syllables;
    }

    public String getWord() {
        return word;
    }

    public Double getFrequency() {

        return frequency;
    }
}
