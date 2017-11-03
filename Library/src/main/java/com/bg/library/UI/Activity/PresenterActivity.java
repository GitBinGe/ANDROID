package com.bg.library.UI.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bg.library.UI.Presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/8/31.
 * 所有Activity的基类
 */

public class PresenterActivity extends PermissionsActivity {

    /**
     * HTPresenter列表
     */
    private List<Presenter> mPresenters = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 添加Presenter
     *
     * @param presenter
     * @return
     */
    public boolean addPresenter(Presenter presenter) {
        mPresenters.add(presenter);
        return false;
    }

    /**
     * 删除Presenter
     *
     * @param presenter
     * @return
     */
    public boolean removePresenter(Presenter presenter) {
        presenter.onDestroy();
        mPresenters.remove(presenter);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (Presenter p : mPresenters) {
            p.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (Presenter p : mPresenters) {
            p.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Presenter p : mPresenters) {
            p.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (Presenter p : mPresenters) {
            p.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Presenter p : mPresenters) {
            p.onDestroy();
        }
        mPresenters.clear();
    }
}
