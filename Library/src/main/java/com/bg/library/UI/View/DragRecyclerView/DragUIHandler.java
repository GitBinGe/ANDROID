package com.bg.library.UI.View.DragRecyclerView;


import com.bg.library.UI.View.DragRecyclerView.indicator.DragIndicator;

public interface DragUIHandler {

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    void onUIReset(DragFrameLayout frame);

    /**
     * prepare for loading
     *
     * @param frame
     */
    void onUIRefreshPrepare(DragFrameLayout frame);

    /**
     * perform refreshing UI
     */
    void onUIRefreshBegin(DragFrameLayout frame);

    /**
     * perform UI after refresh
     */
    void onUIRefreshComplete(DragFrameLayout frame);

    void onUIPositionChange(DragFrameLayout frame, boolean isUnderTouch, byte status, DragIndicator dragIndicator);
}
