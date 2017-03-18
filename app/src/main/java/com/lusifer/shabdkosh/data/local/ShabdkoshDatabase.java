package com.lusifer.shabdkosh.data.local;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;

@ContentProvider(authority = ShabdkoshDatabase.AUTHORITY,
        database = ShabdkoshDatabase.class,
        baseContentUri = ShabdkoshDatabase.BASE_CONTENT_URI)
@Database(name = ShabdkoshDatabase.NAME, version = ShabdkoshDatabase.VERSION)
public class ShabdkoshDatabase {

    public static final String NAME = "ShabdkoshDatabase";

    public static final int VERSION = 1;

    public static final String AUTHORITY = "com.lusifer.shabdkosh";

    public static final String BASE_CONTENT_URI = "content://";

}
