package com.bg.library.UI.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bg.library.UI.Drawable.BackDrawable;
import com.bg.library.UI.Drawable.CloseDrawable;
import com.bg.library.UI.Drawable.MenuDrawable;
import com.bg.library.UI.Drawable.ShapeDrawable;

/**
 * Created by BinGe on 2017/9/26.
 * APP中能用的title
 */

public class Title extends FrameLayout implements View.OnClickListener{

    public class Unit {
        private static final int FIRST  = 0X0001;       //0000 0000 0000 0001
        public static final int BACK    = FIRST << 1;   //0000 0000 0000 0010
        public static final int CLOSE   = FIRST << 2;   //0000 0000 0000 0100
        public static final int MENU    = FIRST << 3;   //0000 0000 0000 1000
        public static final int TEXT    = FIRST << 4;   //0000 0000 0001 0000
    }

    private Paint mPaint;
    private TextView mTextView;//title

    private LinearLayout mLeft;//左边按钮的容器
    private TextView mBackView;//返回按钮
    private TextView mCloseView;//关闭按钮
    private TextView mMenuView;//菜单按钮

    public Title(@NonNull Context context) {
        this(context, null);
    }

    public Title(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Title(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffcccccc);
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        float scale = dm.density;
        mPaint.setStrokeWidth(scale);

        mTextView = new TextView(getContext());
        Object tag = getTag();
        if (tag != null && tag instanceof String) {
            mTextView.setText(tag.toString());
        }
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        mTextView.setTextColor(Color.BLACK);
        addView(mTextView, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        mLeft = new LinearLayout(getContext());
        mLeft.setOrientation(LinearLayout.HORIZONTAL);
        addView(mLeft, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.LEFT));

        mBackView = new TextView(getContext());
        mBackView.setBackground(new BackDrawable(getContext()));
        mLeft.addView(mBackView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackOnClickListener(this);

        mCloseView = new TextView(getContext());
        mCloseView.setBackground(new CloseDrawable(getContext()));
        mLeft.addView(mCloseView, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER | Gravity.LEFT));
        setCloseOnClickListener(this);

        mMenuView = new TextView(getContext());
        mMenuView.setBackground(new MenuDrawable(getContext()));
        addView(mMenuView, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER | Gravity.RIGHT));
        setMenuOnClickListener(this);

        setUnit(Unit.TEXT);

        setColor(Color.BLACK);
    }

    public void setColor(int color) {
        mTextView.setTextColor(color);

        ShapeDrawable back = (ShapeDrawable) mBackView.getBackground();
        back.setColor(color);

        ShapeDrawable close = (ShapeDrawable) mCloseView.getBackground();
        close.setColor(color);

        ShapeDrawable menu = (ShapeDrawable) mMenuView.getBackground();
        menu.setColor(color);
    }

    public void setBottomLineColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //画一条线
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
    }

    /**
     * 设置title文本
     *
     * @param titleText
     */
    public void setTitle(String titleText) {
        mTextView.setText(titleText);
    }

    /**
     * 设置显示哪些部件，返回按钮，菜单按钮等
     *
     * @param unit
     */
    public void setUnit(int unit) {
        if ((unit & Unit.BACK) == Unit.BACK) {
            mBackView.setVisibility(View.VISIBLE);
        } else {
            mBackView.setVisibility(View.GONE);
        }
        if ((unit & Unit.CLOSE) == Unit.CLOSE) {
            mCloseView.setVisibility(View.VISIBLE);
        } else {
            mCloseView.setVisibility(View.GONE);
        }
        if ((unit & Unit.MENU) == Unit.MENU) {
            mMenuView.setVisibility(View.VISIBLE);
        } else {
            mMenuView.setVisibility(View.GONE);
        }
        if ((unit & Unit.TEXT) == Unit.TEXT) {
            mTextView.setVisibility(View.VISIBLE);
        } else {
            mTextView.setVisibility(View.GONE);
        }
    }

    public void setBackOnClickListener(OnClickListener l) {
        if (mBackView != null) {
            mBackView.setOnClickListener(l);
        }
    }

    public void setCloseOnClickListener(OnClickListener l) {
        if (mCloseView != null) {
            mCloseView.setOnClickListener(l);
        }
    }

    public void setMenuOnClickListener(OnClickListener l) {
        if (mMenuView != null) {
            mMenuView.setOnClickListener(l);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBackView) {
            back();
        } else if (v == mMenuView) {
            menu();
        }
    }

    private void back() {
        if (getContext() instanceof Activity) {
            Activity a = (Activity)getContext();
            a.finish();
        }
    }

    private void menu() {

    }

}