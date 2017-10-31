package com.bg.lib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bg.library.UI.Dialog.Alert;
import com.bg.library.UI.Dialog.Prompt;
import com.bg.library.UI.Drawable.LoadingDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = new ImageView(this);
        imageView.setBackground(new LoadingDrawable(this));
        setContentView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Alert(v.getContext())
                        .setTitle("123")
                        .setContent("456")
                        .setButton1("取消", null)
                        .setButton2("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Prompt.show(v.getContext(), "123123");
                            }
                        }).show();
            }
        });


    }
}
