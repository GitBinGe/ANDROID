package com.bg.lib;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bg.library.UI.Dialog.ActionSheet;
import com.bg.library.UI.Dialog.Alert;
import com.bg.library.UI.Dialog.Progress;
import com.bg.library.UI.Drawable.LoadingDrawable;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = new View(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheet as = new ActionSheet(v.getContext());
                as.addItem("123");
                as.addItem("123");
                as.addItem("123");
                as.setTitle("我多我我我我我我");
                as.show();
            }
        });


        int id = R.anim.activity_alpha_in;
        int id2 = R.layout.activity_main;

        FrameLayout frameLayout = new FrameLayout(this);

        ImageView imageView = new ImageView(this);
//        imageView.setBackgroundColor(Color.RED);
        imageView.setImageDrawable(new LoadingDrawable(this));
        imageView.setBackground(new LoadingDrawable(this));
//        frameLayout.addView(imageView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        setContentView(imageView);



//        Progress.show(this);

//        Progress.Base object = Progress.create(this).setType(new Progress.Base());

//        Log.d("123", "123");

//        new Progress(this).show();
//                new Progress(this).show();

//        Alert.show(this);

//        Alert alert = new Alert(this);
//        alert.setTitle("123")
//                .setContent("456")
//                .show();

//        HTProgress.create(this, "123").setType(HTProgress.TYPE.LOADING).show();

        ActionSheet as = new ActionSheet(this);
        as.addItem("123");
        as.addItem("456");
        as.addItem("789");
//        as.setTitle("456");
        as.show();

        new Thread() {
            @Override
            public void run() {
                String url = "http://facility.kwaijian.com/Api/Client/mobileInterface/uploadImage";
                File file = new File("");
            }
        }.start();

    }
}
