package com.bg.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bg.library.UI.Activity.PresenterActivity;
import com.bg.library.Utils.Image.ImageCache;
import com.bg.library.Utils.Log.LogUtils;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends PresenterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            BitmapRegionDecoder regionDecoder = BitmapRegionDecoder.newInstance(getAssets().open("test/00.jpg"), true);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = regionDecoder.decodeRegion(new Rect(0, 0, 1920, 2000), options);
            int size = bitmap.getRowBytes() * bitmap.getHeight();
            LogUtils.d("size : " + size / 1024f);
            ImageView iv = (ImageView) findViewById(R.id.image_test);
            iv.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

