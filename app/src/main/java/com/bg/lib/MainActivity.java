package com.bg.lib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bg.library.UI.Dialog.ActionSheet;
import com.bg.library.UI.Dialog.Progress;

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

        setContentView(view);


//        Progress.Base object = Progress.create(this).setType(new Progress.Base());

//        Log.d("123", "123");

        new Progress(this).show();
//                new Progress(this).show();

//        HTProgress.create(this, "123").setType(HTProgress.TYPE.LOADING).show();

    }
}
