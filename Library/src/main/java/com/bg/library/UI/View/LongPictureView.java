package com.bg.library.UI.View;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bg.library.UI.Drawable.LoadingDrawable;
import com.bg.library.UI.View.ScaleImageView.*;

import java.io.File;

/**
 * Created by BinGe on 2017/10/24.
 * 可以加载大图的view
 */

public class LongPictureView extends SubsamplingScaleImageView {

    private boolean touchable = true;
    private String path;

    public LongPictureView(Context context) {
        this(context, null);
    }

    public LongPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        setBackground(new LoadingDrawable(context));
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (touchable) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }

    public void setImage(File file) {
        float viewWidth = getWidth();
        if (viewWidth == 0) {
            this.path = file.getAbsolutePath();
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        float bitmapWidth = options.outWidth;
        float scale = viewWidth / bitmapWidth;
        setMinScale(scale);//最小显示比例
        setMaxScale(scale * 2.5F);//最大显示比例
        ImageSource source = ImageSource.uri(file.getAbsolutePath());
        ImageViewState state = new ImageViewState(scale, new PointF(0, 0), 0);
        setImage(source, state);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldw != w && w > 0) {
            if (this.path != null) {
                setImage(new File(this.path));
            }
            this.path = null;
        }
    }
}

