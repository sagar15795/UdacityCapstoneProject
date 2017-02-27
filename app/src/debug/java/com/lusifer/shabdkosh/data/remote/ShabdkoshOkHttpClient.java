package com.lusifer.shabdkosh.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ShabdkoshOkHttpClient {


    public OkHttpClient getWpApiOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //Enable Full Body Logging
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Interceptor :> Full Body Logger
        builder.addInterceptor(logger);

        builder.addNetworkInterceptor(new StethoInterceptor());
        return builder.build();

    }
}