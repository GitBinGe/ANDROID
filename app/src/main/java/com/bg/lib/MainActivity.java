package com.bg.lib;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.bg.library.UI.Activity.PresenterActivity;
import com.bg.library.UI.Dialog.ActionSheet;
import com.bg.library.UI.View.TitleView;


public class MainActivity extends PresenterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.image_test);
        TitleView titleView = (TitleView) findViewById(R.id.title);
        titleView.setUnit(TitleView.Unit.BACK| TitleView.Unit.TEXT| TitleView.Unit.MENU);
        titleView.setColor(Color.BLACK);
        titleView.setTitle("Main Activity Title");
        titleView.setMenuOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheet menu = new ActionSheet(MainActivity.this);
                menu.addItem("广告欺诈");
                menu.addItem("淫秽色情");
                menu.addItem("骚扰谩骂");
                menu.addItem("反动政治");
                menu.addItem("其他内容");
                menu.show();
            }
        });
    }

}

