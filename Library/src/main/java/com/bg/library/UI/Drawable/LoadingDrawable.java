package com.bg.library.UI.Drawable;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.LinearInterpolator;

/**
 * Created by BinGe on 2017/9/26.
 * loading的背景效果
 */

public class LoadingDrawable extends ShapeDrawable {

    private RectF rect = new RectF();
    private ValueAnimator animator;
    private Paint mPaint = new Paint();

    public LoadingDrawable(Context context) {
        super(context);
        float width = getDensityScale() * 36;
        rect.set(0, 0, width, width);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getDensityScale() * 1);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        animator = ValueAnimator.ofInt(0, 359);
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Log.d("loading", canvas.getWidth()+":"+canvas.getHeight());
        rect.offsetTo(canvas.getWidth() / 2 - rect.width() / 2, canvas.getHeight() / 2 - rect.height() / 2);
        mPaint.setColor(Color.GREEN);

        canvas.drawRect(rect, mPaint);
        Log.d("loading",rect.toString());
//        canvas.drawArc(rect, (int) animator.getAnimatedValue(), 320, false, mPaint);
//        invalidateSelf();
    }

    @Override
    public Paint getPaint() {
        return mPaint;
    }

}
