package com.lusifer.shabdkosh.data.model.word;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Syllables {

    @SerializedName("count")
    private Long count;
    @SerializedName("list")
    private List<String> list;

    public Long getCount() {
        return count;
    }

    public List<String> getList() {
        return list;
    }
}
