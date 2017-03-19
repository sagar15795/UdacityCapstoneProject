package com.lusifer.shabdkosh.data.model.search;

import com.google.gson.annotations.SerializedName;


public class SearchResult {

    @SerializedName("results")
    private Results mResults;

    public Results getResults() {
        return mResults;
    }

    public void setResults(Results results) {
        mResults = results;
    }

}
