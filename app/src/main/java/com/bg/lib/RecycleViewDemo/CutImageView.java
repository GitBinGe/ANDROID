package com.bg.lib.RecycleViewDemo;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.bg.library.Utils.Log.LogUtils;

/**
 * Created by BinGe on 2018/1/4.
 */

public class CutImageView extends AppCompatImageView {

    private Rect mRect;

    public CutImageView(Context context) {
        super(context);
    }

    public CutImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (mRect == null) {
            setMeasuredDimension(widthSize, widthSize);
            LogUtils.d("height size  : " + widthSize);
        } else {
            float scale =  mRect.width() / (float)widthSize;
            int height = (int) (mRect.height() / scale);
            setMeasuredDimension(widthSize, height);
            LogUtils.d("height size  : " + height);
        }
        LogUtils.d("rect : " + mRect);
    }

    public void setRect(Rect rect) {
        this.mRect = rect;
        requestLayout();
    }

}
