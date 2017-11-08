package com.bg.lib;

import android.graphics.Color;
import android.os.Bundle;

import com.bg.library.UI.Activity.PresenterActivity;
import com.bg.library.UI.View.TitleView;


public class MainActivity extends PresenterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.image_test);
        TitleView titleView = (TitleView) findViewById(R.id.title);
        titleView.setUnit(TitleView.Unit.BACK| TitleView.Unit.TEXT| TitleView.Unit.ADD);
        titleView.setColor(Color.BLACK);
        titleView.setTitle("Main Activity Title");
    }

}

