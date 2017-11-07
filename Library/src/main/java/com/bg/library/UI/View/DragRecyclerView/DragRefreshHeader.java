package com.bg.library.UI.View.DragRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bg.library.R;
import com.bg.library.UI.View.DragRecyclerView.indicator.DragIndicator;
import com.bg.library.UI.View.SmileLoadingView;

public class DragRefreshHeader extends FrameLayout implements DragUIHandler {

    private SmileLoadingView mLoadingView;

    public DragRefreshHeader(@NonNull Context context) {
        this(context, null);
    }

    public DragRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.drag_refresh_header, this);

        mLoadingView = (SmileLoadingView) header.findViewById(R.id.loading);

        initUIs();
    }

    /**
     * 初始化UI
     */
    private void initUIs() {

    }


    @Override
    public void onUIReset(DragFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(DragFrameLayout frame) {
        mLoadingView.stop();
    }

    @Override
    public void onUIRefreshBegin(DragFrameLayout frame) {
        mLoadingView.start();
    }

    @Override
    public void onUIRefreshComplete(DragFrameLayout frame) {
        mLoadingView.hide();
    }

    @Override
    public void onUIPositionChange(DragFrameLayout frame, boolean isUnderTouch, byte status, DragIndicator dragIndicator) {

    }
}
