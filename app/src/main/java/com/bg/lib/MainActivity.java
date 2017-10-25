package com.bg.lib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bg.library.UI.Dialog.ActionSheet;

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
                as.show();
            }
        });


        setContentView(view);
    }
}
