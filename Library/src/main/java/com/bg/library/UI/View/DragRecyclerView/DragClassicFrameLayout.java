package com.bg.library.UI.View.DragRecyclerView;

import android.content.Context;
import android.util.AttributeSet;

import com.bg.library.UI.View.DragRecyclerView.loadmore.DefaultLoadMoreViewFooter;
import com.bg.library.UI.View.DragRecyclerView.loadmore.ILoadMoreViewFactory;


public class DragClassicFrameLayout extends DragFrameLayout {

    private DragClassicDefaultHeader mDragClassicHeader;

    public DragClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public DragClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public DragClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mDragClassicHeader = new DragClassicDefaultHeader(getContext());
        setHeaderView(mDragClassicHeader);
        addDragUIHandler(mDragClassicHeader);

        ILoadMoreViewFactory loadMoreViewFactory = new DefaultLoadMoreViewFooter();
        setFooterView(loadMoreViewFactory);
    }

    public DragClassicDefaultHeader getHeader() {
        return mDragClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mDragClassicHeader != null) {
            mDragClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mDragClassicHeader != null) {
            mDragClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }
}
