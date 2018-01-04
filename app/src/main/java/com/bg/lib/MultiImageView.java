package com.bg.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.bg.library.Utils.Log.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2018/1/3.
 * 支持多张长图的view
 */

public class MultiImageView extends LinearLayout {

    public MultiImageView(Context context) {
        this(context, null);
    }

    public MultiImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(Color.RED);
    }

    public void addImage(String path) {
        LongImageView view = new LongImageView(getContext());
        view.setPath(path);
        addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.d("MultiImageView onScrollChanged: " + this + " -- " + t);
    }

    public void onScroll() {
//        int[] location = new int[2];
//        getLocationInWindow(location);
//        LogUtils.d("MultiImageView onScroll: " + this + " -- " + location[1]);

        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).invalidate();
        }
    }

    class LongImageView extends View {
        int width;
        int height;
        String path;
        BitmapRegionDecoder bitmapRegionDecoder;

        LongImageView(Context context) {
            super(context);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            int[] location = new int[2];
            MultiImageView.this.getLocationInWindow(location);
            LogUtils.d("MultiImageView : " + this + " -- " + location[1]);
            //设置显示图片的中心区域
            if (this.bitmapRegionDecoder != null) {
//                float
                Bitmap bitmap = bitmapRegionDecoder.decodeRegion(
                        new Rect(0, 0, 1080, 1920), null);

                canvas.drawBitmap(bitmap, 0, 0, null);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = 0;
            if (width > 0 && height > 0) {
                float scale = widthSize / (float) width;
                heightSize = (int) (height * scale);
            }
            setMeasuredDimension(widthSize, heightSize);
        }

        void setPath(String path) {
            try {
                InputStream is = getContext().getAssets().open(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, null, options);
                this.width = options.outWidth;
                this.height = options.outHeight;
                this.path = path;

                bitmapRegionDecoder = BitmapRegionDecoder.newInstance(getContext().getAssets().open(path), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
