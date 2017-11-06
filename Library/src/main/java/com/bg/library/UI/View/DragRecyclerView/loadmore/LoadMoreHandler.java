package com.bg.library.UI.View.DragRecyclerView.loadmore;

import android.view.View;
import android.view.View.OnClickListener;

import static com.bg.library.UI.View.DragRecyclerView.loadmore.ILoadMoreViewFactory.ILoadMoreView;


public interface LoadMoreHandler {

    /**
     * @param contentView
     * @param loadMoreView
     * @param onClickLoadMoreListener
     * @return 是否有 init ILoadMoreView
     */
    boolean handleSetAdapter(View contentView, ILoadMoreView loadMoreView, OnClickListener
            onClickLoadMoreListener);

    void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);

    void removeFooter();

    void addFooter();
}
