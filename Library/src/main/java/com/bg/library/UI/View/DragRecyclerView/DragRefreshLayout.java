package com.bg.library.UI.View.DragRecyclerView;

import android.content.Context;
import android.util.AttributeSet;

import com.bg.library.UI.View.DragRecyclerView.loadmore.DefaultLoadMoreViewFooter;
import com.bg.library.UI.View.DragRecyclerView.loadmore.ILoadMoreViewFactory;


public class DragRefreshLayout extends DragFrameLayout {

    private DragRefreshHeader mDragRefreshHeader;
    private ILoadMoreViewFactory mLoadMoreViewFactory;

    public DragRefreshLayout(Context context) {
        super(context);
        initViews();
    }

    public DragRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public DragRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mDragRefreshHeader = new DragRefreshHeader(getContext());
        setHeaderView(mDragRefreshHeader);
        addDragUIHandler(mDragRefreshHeader);
        setLoadingCycleTime(1500); // loading动画周期

        mLoadMoreViewFactory = new DefaultLoadMoreViewFooter();
        setFooterView(mLoadMoreViewFactory);
    }

    public void setHeadLoadingColor(int color) {
        if (mDragRefreshHeader != null) {
            mDragRefreshHeader.setLoadingColor(color);
        }
    }

    public DragRefreshHeader getHeader() {
        return mDragRefreshHeader;
    }
}
