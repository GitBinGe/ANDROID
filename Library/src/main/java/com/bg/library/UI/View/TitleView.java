package com.bg.library.UI.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bg.library.UI.Drawable.AddDrawable;
import com.bg.library.UI.Drawable.BackDrawable;
import com.bg.library.UI.Drawable.CloseDrawable;
import com.bg.library.UI.Drawable.ListDrawable;
import com.bg.library.UI.Drawable.MenuDrawable;
import com.bg.library.UI.Drawable.SearchDrawable;
import com.bg.library.UI.Drawable.ShapeDrawable;
import com.bg.library.UI.Drawable.SureDrawable;

/**
 * Created by BinGe on 2017/9/26.
 * APP中能用的title
 */

public class TitleView extends FrameLayout implements View.OnClickListener {

    public class Unit {
        private static final int FIRST = 0X0001;       //0000 0000 0000 0001
        public static final int BACK = FIRST << 1;   //0000 0000 0000 0010
        public static final int CLOSE = FIRST << 2;   //0000 0000 0000 0100
        public static final int MENU = FIRST << 3;   //0000 0000 0000 1000
        public static final int TEXT = FIRST << 4;   //0000 0000 0001 0000
        public static final int ADD = FIRST << 5;   //0000 0000 0010 0000
        public static final int SEARCH = FIRST << 6;   //0000 0000 0100 0000
        public static final int SURE = FIRST << 7;   //0000 0000 1000 0000
        public static final int LIST = FIRST << 8;   //0000 0001 0000 0000
    }

    private Paint mPaint;
    private TextView mTextView;//title

    private LinearLayout mLeft;//左边按钮的容器
    private TextView mBackView;//返回按钮
    private TextView mCloseView;//关闭按钮
    private TextView mMenuView;//菜单按钮

    private LinearLayout mRight;//右边按钮的容器
    private TextView mAddView;//添加按钮
    private TextView mSearchView;//搜索按钮
    private TextView mSureView;//搜索按钮
    private TextView mListView;//列表按钮

    public TitleView(@NonNull Context context) {
        this(context, null);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setTranslationZ(2);
//        }

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
        mTextView.setSingleLine();
        mTextView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        int p = (int) (100 * scale);
        mTextView.setPadding(p, 0, p, 0);
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

        mRight = new LinearLayout(getContext());
        mRight.setOrientation(LinearLayout.HORIZONTAL);
        addView(mRight, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.RIGHT));

        mSearchView = new TextView(getContext());
        mSearchView.setBackground(new SearchDrawable(getContext()));
        mRight.addView(mSearchView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setSearchOnClickListener(this);

        mAddView = new TextView(getContext());
        mAddView.setBackground(new AddDrawable(getContext()));
        mRight.addView(mAddView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setAddOnClickListener(this);

        mListView = new TextView(getContext());
        mListView.setBackground(new ListDrawable(getContext()));
        mRight.addView(mListView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setSureOnClickListener(this);

        mSureView = new TextView(getContext());
        mSureView.setBackground(new SureDrawable(getContext()));
        mRight.addView(mSureView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setSureOnClickListener(this);


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

        ShapeDrawable add = (ShapeDrawable) mAddView.getBackground();
        add.setColor(color);

        ShapeDrawable sure = (ShapeDrawable) mSureView.getBackground();
        sure.setColor(color);

        ShapeDrawable list = (ShapeDrawable) mListView.getBackground();
        list.setColor(color);
    }

    public void setBottomLineColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //画一条线
//        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
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
        if ((unit & Unit.SEARCH) == Unit.SEARCH) {
            mSearchView.setVisibility(View.VISIBLE);
        } else {
            mSearchView.setVisibility(View.GONE);
        }
        if ((unit & Unit.ADD) == Unit.ADD) {
            mAddView.setVisibility(View.VISIBLE);
        } else {
            mAddView.setVisibility(View.GONE);
        }
        if ((unit & Unit.SURE) == Unit.SURE) {
            mSureView.setVisibility(View.VISIBLE);
        } else {
            mSureView.setVisibility(View.GONE);
        }
        if ((unit & Unit.LIST) == Unit.LIST) {
            mListView.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.GONE);
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

    public void setSearchOnClickListener(OnClickListener l) {
        if (mSearchView != null) {
            mSearchView.setOnClickListener(l);
        }
    }

    public void setAddOnClickListener(OnClickListener l) {
        if (mAddView != null) {
            mAddView.setOnClickListener(l);
        }
    }

    public void setSureOnClickListener(OnClickListener l) {
        if (mSureView != null) {
            mSureView.setOnClickListener(l);
        }
    }

    public void setListOnClickListener(OnClickListener l) {
        if (mListView != null) {
            mListView.setOnClickListener(l);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {

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
            Activity a = (Activity) getContext();
            a.finish();
        }
    }

    private void menu() {

    }

}