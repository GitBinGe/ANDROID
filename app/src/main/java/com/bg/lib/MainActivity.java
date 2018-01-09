package com.bg.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        new Thread() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("test/test.jpg"), null, null);
                    int index = 0;
                    while (true) {
                        String key = "test" + index++;
                        ImageCache.get().put(key, bitmap);
                        SystemClock.sleep(2000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

}

