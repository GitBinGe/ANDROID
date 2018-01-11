package com.bg.library.UI.View;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2018/1/11.
 * 大图
 */

public class LargeImageView extends View {

    private List<String> images = new ArrayList<>();

    public LargeImageView(Context context) {
        super(context);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    public void addImage(String path) {
        images.add(path);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int location[] = new int[2];
        getLocationInWindow(location);
    }

    public void onScroll() {
        invalidate();
    }

}
