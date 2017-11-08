package com.bg.library.UI.View.DragRecyclerView.loadmore;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.bg.library.UI.View.DragRecyclerView.recyclerview.RecyclerAdapterWithHF;


public class RecyclerViewHandler implements LoadMoreHandler {

    private RecyclerView.OnScrollListener mOnScrollListener;
    RecyclerViewOnScrollListener mRecyclerViewOnScrollListener;
    private RecyclerAdapterWithHF mRecyclerAdapter;
    private View mFooter;

    @Override
    public boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        boolean hasInit = false;
        mRecyclerAdapter = (RecyclerAdapterWithHF) recyclerView.getAdapter();
        if (loadMoreView != null) {
            final Context context = recyclerView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadMoreViewFactory.FootViewAdder() {

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, recyclerView, false);
                    mFooter = view;
                    return addFootView(view);
                }

                @Override
                public View addFootView(View view) {
                    mRecyclerAdapter.addFooter(view);
                    return view;
                }
            }, onClickLoadMoreListener);
            hasInit = true;
        }
        return hasInit;
    }

    @Override
    public void addFooter() {
        if (mRecyclerAdapter.getFootSize() <= 0 && null != mFooter) {
            mRecyclerAdapter.addFooter(mFooter);
        }
    }

    @Override
    public void removeFooter() {
        if (mRecyclerAdapter.getFootSize() > 0 && null != mFooter) {
            mRecyclerAdapter.removeFooter(mFooter);
        }
    }

    @Override
    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mOnScrollListener = listener;
        if (mRecyclerViewOnScrollListener != null) {
            mRecyclerViewOnScrollListener.addOnScrollListener(mOnScrollListener);
        }
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        mRecyclerViewOnScrollListener = new RecyclerViewOnScrollListener(onScrollBottomListener);
        if (mOnScrollListener != null) {
            mRecyclerViewOnScrollListener.addOnScrollListener(mOnScrollListener);
        }
        recyclerView.addOnScrollListener(mRecyclerViewOnScrollListener);
    }

    /**
     * 滑动监听
     */
    private static class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;
        private RecyclerView.OnScrollListener mOnScrollListener;

        public RecyclerViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
            mOnScrollListener = listener;
        }

        @Override
        public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isScrollBottom(recyclerView)) {
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScrollBottom();
                }
            }
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        }

        private boolean isScrollBottom(RecyclerView recyclerView) {
            return !isCanScrollVertically(recyclerView);
        }

        private boolean isCanScrollVertically(RecyclerView recyclerView) {
            if (android.os.Build.VERSION.SDK_INT < 14) {
                return ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < recyclerView.getHeight();
            } else {
                return ViewCompat.canScrollVertically(recyclerView, 1);
            }
        }

        @Override
        public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrolled(recyclerView, dx, dy);
            }
        }

    }

}
