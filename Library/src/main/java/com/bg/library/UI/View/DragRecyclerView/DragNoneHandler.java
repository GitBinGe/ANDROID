package com.bg.library.UI.View.DragRecyclerView;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;

public abstract class DragNoneHandler implements DragHandler {

    @Override
    public boolean checkCanDoRefresh(DragFrameLayout frame, View content, View header) {
        return false;
    }
}