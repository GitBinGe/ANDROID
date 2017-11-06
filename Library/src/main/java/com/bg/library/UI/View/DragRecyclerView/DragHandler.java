package com.bg.library.UI.View.DragRecyclerView;

import android.view.View;

public interface DragHandler {

    /**
     * Check can do refresh or not. For example the content is empty or the first child is in view.
     * <p/>
     * {@link DragDefaultHandler#checkContentCanBePulledDown}
     */
    boolean checkCanDoRefresh(final DragFrameLayout frame, final View content, final View header);

    /**
     * When refresh begin
     *
     * @param frame
     */
    void onRefreshBegin(final DragFrameLayout frame);
}