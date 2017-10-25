package com.bg.library.UI.Presenter;

/**
 * Created by BinGe on 2017/9/1.
 * HTPresenter与View之间的回调接口
 */

public interface UIActionListener {

    /**
     * view
     * @param action
     * @param object
     */
    void onUIAction(int action, Object object);

}