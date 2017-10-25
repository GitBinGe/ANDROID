package com.bg.library.UI.Drawable;

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

/**
 * Created by BinGe on 2017/9/26.
 * loading的背景效果
 */

public class LoadingDrawable extends Drawable {

    /**
     * 加载动画
     */
    private static LruCache<String, Bitmap> sBitmap;

    /**
     * 用于计算loading时间
     */
    private long time;

    /**
     * Drawable宽高
     */
    private float mSize;

    /**
     * 分辨率比例
     */
    private float mScale = 1;

    private Paint mPaint;


    public LoadingDrawable(Context context) {

        if (sBitmap == null) {
            int maxMemory = 4;
            sBitmap = new LruCache<String, Bitmap>(maxMemory) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight() / 1024 / 1024;
                }
            };
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScale = dm.density;
        mSize = mScale * 36;

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mSize = Math.min(mSize, Math.min(canvas.getWidth(), canvas.getHeight()));

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (time == 0) {
            time = System.currentTimeMillis();
        }
        float t = (System.currentTimeMillis() - time) / 1000f;
        t = t % 1;
        int angle = (int) (360 * t);
        canvas.save();
        canvas.rotate(angle, width / 2, height / 2);

        Bitmap loading = getLoadingBitmap();
        canvas.drawBitmap(loading,
                width / 2 - loading.getWidth() / 2,
                height / 2 - loading.getHeight() / 2, null);
        canvas.restore();

        invalidateSelf();

    }

    /**
     * 创建loading图片
     *
     * @return
     */
    private Bitmap getLoadingBitmap() {
        int size = (int) mSize;
        int color = mPaint.getColor();
        String key = size + "-" + color;

        Bitmap bitmap = sBitmap.get(key);
        if (bitmap != null && !bitmap.isRecycled()) {
            return bitmap;
        }
        bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        Paint p = new Paint(mPaint);
        float stroke = size * 0.02f;
        p.setStrokeWidth(size * 0.03f);
        p.setStyle(Paint.Style.STROKE);
        p.setAntiAlias(true);
        p.setAlpha(150);
        c.drawArc(new RectF(0 + stroke, 0 + stroke, size - stroke, size - stroke), 20, 360 - 40, false, p);

        sBitmap.put(key, bitmap);
        return bitmap;
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mSize;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mSize;
    }

}
