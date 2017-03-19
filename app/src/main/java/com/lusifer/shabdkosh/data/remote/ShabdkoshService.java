package com.lusifer.shabdkosh.data.remote;


import com.lusifer.shabdkosh.BuildConfig;
import com.lusifer.shabdkosh.data.model.search.SearchResult;
import com.lusifer.shabdkosh.data.model.word.WordDetail;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ShabdkoshService {

    @GET(APIEndPointConstant.WORDS)
    @Headers({APIEndPointConstant.HEADER_MASHAPE_KEY + BuildConfig.API_KEY})
    Observable<WordDetail> getWord(@Path(APIEndPointConstant.PATH_WORD) String word);


    @GET(APIEndPointConstant.SEARCH)
    @Headers({APIEndPointConstant.HEADER_MASHAPE_KEY + BuildConfig.API_KEY})
    Observable<SearchResult> getSearchSuggestionWordList(@Query(APIEndPointConstant
            .SEARCH_QUERY_LETTERPATTERN) String pattern);


}
