package com.bg.library.UI.Dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Progress
 */
public class Alert extends BaseDialog {

    private String title;
    private String content;
    private String button1;
    private View.OnClickListener click1;
    private String button2;
    private View.OnClickListener click2;

    public Alert(@NonNull Context context) {
        super(context, false);

    }

    public Alert setTitle(String title) {
        this.title = title;
        return this;
    }

    public Alert setContent(String content) {
        this.content = content;
        return this;
    }

    public Alert setButton1(String name, View.OnClickListener listener) {
        this.button1 = name;
        this.click1 = listener;
        return this;
    }

    public Alert setButton2(String name, View.OnClickListener listener) {
        this.button2 = name;
        this.click2 = listener;
        return this;
    }

    @Override
    public void show() {
        FrameLayout root = new FrameLayout(getContext());
        root.addView(new ConfirmView(getContext(), title, content, button1, click1, button2, click2));
        setContentView(root);
        super.show();
    }

    class ConfirmView extends LinearLayout {

        public ConfirmView(Context context,
                           String title,
                           String content,
                           String cancel,
                           OnClickListener cancelListener,
                           String sure,
                           OnClickListener sureListener) {
            super(context);

//            setLayerType(View.LAYER_TYPE_HARDWARE, null);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(getDp(250),
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            setLayoutParams(params);

            LinearLayout parent = new LinearLayout(context);
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setBackgroundColor(Color.WHITE);
            addView(parent, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            if (TextUtils.isEmpty(content)) {
                content = " ";
            }
            if (TextUtils.isEmpty(sure)) {
                sure = "确认";
            }

            int contentPaddingTop = 10;
            if (!TextUtils.isEmpty(title)) {
                //标题
                TextView titleView = new TextView(context);
                titleView.setText(title);
                titleView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                titleView.setPadding(getDp(10), getDp(20), getDp(10), getDp(0));
                titleView.getPaint().setFakeBoldText(true);
                titleView.setTextColor(Color.BLACK);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                titleView.setSingleLine();
                parent.addView(titleView);
                contentPaddingTop = 0;
            }

            //内容
            TextView contentView = new TextView(context);
            contentView.setText(content);
            contentView.setGravity(Gravity.CENTER);
            contentView.setPadding(getDp(10), getDp(contentPaddingTop), getDp(10), getDp(20));
            contentView.setTextColor(Color.BLACK & 0xccffffff);
            contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            parent.addView(contentView);

            View line = new View(getContext());
            line.setBackgroundColor(Color.BLACK & 0x66ffffff);
            parent.addView(line, LayoutParams.MATCH_PARENT, getDp(1) / 2);

            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            parent.addView(buttonLayout, LayoutParams.MATCH_PARENT, getDp(45));

            if (!TextUtils.isEmpty(cancel)) {
                TextView cancelView = new TextView(context);
                cancelView.setText(cancel);
                cancelView.setGravity(Gravity.CENTER);
                cancelView.setPadding(getDp(10), 0, getDp(10), 0);
                cancelView.setTextColor(0xff2CA1F3);
                cancelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                cancelView.setSingleLine();
                cancelView.setClickable(true);
                cancelView.setBackground(new StateDrawable());
                LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                p1.weight = 1;
                buttonLayout.addView(cancelView, p1);
                cancelView.setOnClickListener(cancelListener);

                line = new View(getContext());
                line.setBackgroundColor(Color.BLACK & 0x66ffffff);
                buttonLayout.addView(line, getDp(1) / 2, LayoutParams.MATCH_PARENT);
            }

            TextView sureView = new TextView(context);
            sureView.setText(sure);
            sureView.setGravity(Gravity.CENTER);
            sureView.setPadding(getDp(10), 0, getDp(10), 0);
            sureView.setBackground(new StateDrawable());
            sureView.setTextColor(0xff2CA1F3);
            sureView.setClickable(true);
            sureView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            sureView.setSingleLine();
            sureView.setOnClickListener(sureListener);
            LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            p1.weight = 1;
            buttonLayout.addView(sureView, p1);


        }


        private Bitmap bitmap;
        private Rect rect = new Rect();
        private Paint paint = new Paint();

        @Override
        protected void dispatchDraw(Canvas canvas) {
            if (bitmap == null) {
                rect.set(0, 0, getWidth() / 2, getHeight() / 2);
                bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                Path p = new Path();
                p.addRoundRect(new RectF(rect), rect.width() * 0.04f, rect.width() * 0.04f, Path.Direction.CCW);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLACK);
                c.drawPath(p, paint);
            }
            rect.set(0, 0, getWidth(), getHeight());
            int sc = canvas.saveLayer(new RectF(rect), null,
                    Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
                            | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                            | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                            | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
            super.dispatchDraw(canvas);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(bitmap, null, rect, paint);
            canvas.restoreToCount(sc);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
        }
    }


    static class StateDrawable extends StateListDrawable {

        public StateDrawable() {
            //获取对应的属性值 Android框架自带的属性 attr
            int pressed = android.R.attr.state_pressed;
            int window_focused = android.R.attr.state_window_focused;
            int focused = android.R.attr.state_focused;
            int selected = android.R.attr.state_selected;

            addState(new int[]{pressed, window_focused}, new Background(Color.BLACK & 0x22ffffff));
//        addState(new int []{pressed , -focused}, new Background(Color.GRAY));
//        addState(new int []{selected }, new Background(Color.YELLOW));
//        addState(new int []{focused }, new Background(Color.GREEN));
            addState(new int[]{}, new Background(Color.TRANSPARENT));

        }

        class Background extends Drawable {

            private int color = Color.BLACK;

            public Background(int color) {
                this.color = color;
            }

            @Override
            public void draw(Canvas canvas) {
                canvas.drawColor(color);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
            }
        }

    }
}