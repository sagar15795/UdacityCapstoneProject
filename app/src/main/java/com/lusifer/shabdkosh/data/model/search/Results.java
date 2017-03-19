package com.lusifer.shabdkosh.data.model.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Results {

    @SerializedName("data")
    private List<String> mData;

    @SerializedName("total")
    private Long mTotal;

    public List<String> getData() {
        return mData;
    }

    public void setData(List<String> data) {
        mData = data;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

}
