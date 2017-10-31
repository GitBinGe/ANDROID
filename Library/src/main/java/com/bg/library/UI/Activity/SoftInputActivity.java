package com.bg.library.UI.Activity;


import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by LooooG on 2017/10/12.
 * 软键盘管理
 */
public class SoftInputActivity extends AnimActivity {

    /**
     * 服务 Service
     */
    protected InputMethodManager imm; // 输入法管理

    protected int mStatusBarHeight = 0; // 系统状态栏高度

    protected OnSoftInputListener mListener; // 软键盘监听器

    protected boolean isObserveInputting = false; // 是否监听软键盘

    private static Handler sHandler;

    private InputMethodManager getImm() {
        if (imm == null) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return imm;
    }

    /**
     * 弹出软键盘
     */
    public void showSoftInput() {
        getImm().toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        getImm().hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 设置软键盘监听器
     *
     * @param l
     */
    public void setOnSoftInputListener(OnSoftInputListener l) {
        mListener = l;
        mStatusBarHeight = getStatusBarHeight(this);
        isObserveInputting = true;

        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            int previousKeyboardHeight = -1;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);

                int displayHeight = rect.bottom - rect.top;
                int height = decorView.getHeight();
                int keyboardHeight = height - displayHeight;
                if (previousKeyboardHeight != keyboardHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    onSoftKeyBoardChange(keyboardHeight, !hide);
                }

                previousKeyboardHeight = height;

            }
        });
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    int previousKeyboardHeight = -1;
    int offsetY = 0;

    private void onSoftKeyBoardChange(int softKeyboardHeight, boolean visible) {
        // LogUtils.d("softKeyboard Height: " + softKeyboardHeight + ", mStatusBarHeight: " + mStatusBarHeight);
        if (!visible) {
            offsetY = mStatusBarHeight - softKeyboardHeight;
        }

        // 聊天室
        final int height = softKeyboardHeight - mStatusBarHeight + offsetY;
        // LogUtils.d("softKeyboard >>>>>>>>>>>>>>>>>>>>>> height: " + height + ", screenHeight: " + SystemInfo.Screen.height + ", visible: " + String.valueOf(visible));
        if (isObserveInputting && previousKeyboardHeight != softKeyboardHeight) {
            // LogUtils.d("observe softKeyboard >>>>>>>>>>>>>>> visible: " + visible);
            // EventBus软键盘事件通知
            if (!visible) {
                if (sHandler == null) {
                    sHandler = new Handler(getMainLooper());
                }
                sHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onSoftInputHide();
                        }
                    }
                }, 200);
            } else {
                if (mListener != null) {
                    mListener.onSoftInputShow();
                }
            }
        }

        previousKeyboardHeight = softKeyboardHeight;
    }

    /**
     * 软键盘监听事件
     */
    public interface OnSoftInputListener {

        /**
         * 软键盘显示事件
         */
        void onSoftInputShow();

        /**
         * 软键盘隐藏事件
         */
        void onSoftInputHide();
    }


}
