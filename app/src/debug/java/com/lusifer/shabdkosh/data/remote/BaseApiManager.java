package com.lusifer.shabdkosh.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApiManager {


    public static final String WP_API_URL = "https://wordsapiv1.p.mashape.com/";


    /********
     * Helper class that sets up a new services with gson converter factory
     *******/

    private <T> T createJsonApi(Class<T> clazz, String ENDPOINT) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new ShabdkoshOkHttpClient().getWpApiOkHttpClient())
                .build();

        return retrofit.create(clazz);
    }

    public ShabdkoshService getWordApi() {
        return createJsonApi(ShabdkoshService.class, WP_API_URL);
    }
}