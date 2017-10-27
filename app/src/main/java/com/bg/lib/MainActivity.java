package com.bg.lib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bg.library.UI.Drawable.LoadingDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = new ImageView(this);
        imageView.setBackground(new LoadingDrawable(this));
        setContentView(imageView);


    }
}
