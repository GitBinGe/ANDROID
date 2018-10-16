package com.bg.library.UI.Drawable;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.bg.library.UI.Animation.ValueAnimation;

/**
 * Created by BinGe on 2017/9/26.
 * loading的背景效果
 */

public class LoadingDrawable extends ShapeDrawable {

    private RectF rect = new RectF();
    private ValueAnimation animator;
    private Paint mPaint;
    private float minWidth;

    public LoadingDrawable(Context context) {
        super(context);
        minWidth = getDensityScale() * 36;
        rect.set(0, 0, minWidth, minWidth);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getDensityScale() * 1);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        animator = new ValueAnimation(1000);
        animator.setRepeatCount(-1);
        animator.start();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float r1 = Math.min(canvas.getWidth(), canvas.getHeight());
        float r = Math.min(r1, minWidth);

        r -= (mPaint.getStrokeWidth() * 2);
        rect.set(0, 0, r, r);
        rect.offsetTo(canvas.getWidth() / 2 - rect.width() / 2, canvas.getHeight() / 2 - rect.height() / 2);
        canvas.drawArc(rect, animator.getValue() * 359, 320, false, mPaint);
        invalidateSelf();
    }


    @Override
    public Paint getPaint() {
        return mPaint;
    }

}
