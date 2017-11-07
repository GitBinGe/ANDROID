package com.bg.lib;

import android.os.Bundle;

import com.bg.library.UI.Activity.PresenterActivity;

public class MainActivity extends PresenterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.image_test);
    }

}

