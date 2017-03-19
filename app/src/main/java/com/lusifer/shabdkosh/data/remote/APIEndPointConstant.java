package com.lusifer.shabdkosh.data.remote;


class APIEndPointConstant {

    public static final String PATH_WORD = "word";
    public static final String WORDS = "words/{" + PATH_WORD + "}";
    public static final String HEADER_MASHAPE_KEY = "X-Mashape-Key:";
    public static final String SEARCH = "words/?limit=5&page=1";
    public static final String SEARCH_QUERY_LETTERPATTERN = "letterPattern";
}
