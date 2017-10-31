package com.bg.lib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bg.library.Base.Objects.JSON.JSON;
import com.bg.library.UI.View.LongPictureView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSON json = new JSON();
//        boolean isEmpty = json.isJSONEmpty();
//        Log.d("","");

        LongPictureView pictureView = new LongPictureView(this);

    }
}
