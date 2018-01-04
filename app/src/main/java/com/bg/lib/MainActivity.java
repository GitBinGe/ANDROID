package com.bg.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bg.library.UI.Activity.PresenterActivity;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends PresenterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        final MultiImageView view = new MultiImageView(this);
//        ScrollView sv = new ScrollView(this) {
//            @Override
//            protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//                super.onScrollChanged(l, t, oldl, oldt);
//                view.onScroll();
//            }
//        };
//
//        try {
//            String[] images = getAssets().list("test");
//            for (String image : images) {
//                view.addImage("test/" + image);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sv.addView(view);

//        setContentView(sv);

        final ImageView iv = new ImageView(this);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream is = getAssets().open("test/111.jpg");
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    iv.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        setContentView(iv);

    }

}

