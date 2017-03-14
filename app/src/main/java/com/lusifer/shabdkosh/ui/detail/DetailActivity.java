package com.lusifer.shabdkosh.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.utils.ActivityUtils;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), DetailFragment
                .newInstance(), R.id.frame_container);

    }
}
