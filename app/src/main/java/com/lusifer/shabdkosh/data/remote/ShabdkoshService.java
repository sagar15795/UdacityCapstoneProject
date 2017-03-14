package com.lusifer.shabdkosh.data.remote;


import com.lusifer.shabdkosh.BuildConfig;
import com.lusifer.shabdkosh.data.model.Word;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

public interface ShabdkoshService {

    @GET(APIEndPointConstant.WORDS)
    @Headers({APIEndPointConstant.HEADER_MASHAPE_KEY+ BuildConfig.API_KEY})
    Observable<Word> getWord(@Path(APIEndPointConstant.PATH_WORD) String word);
}
