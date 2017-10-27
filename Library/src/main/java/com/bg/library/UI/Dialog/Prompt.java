package com.bg.library.UI.Dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Progress
 * Created By LooooG.
 */
public class Prompt {

    private static PromptToast dialogToast;

    public static void show(Context context, String prompt) {
        if (dialogToast == null) {
            dialogToast = new PromptToast(context);
        }
        dialogToast.show(prompt);
    }


    static class PromptToast {
        private Context mContext;
        private Toast toastStart;

        public PromptToast(@NonNull Context context) {
            mContext = context;
            //Toast的初始化
            toastStart = new Toast(getContext());
            //获取屏幕高度
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();
            //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
            toastStart.setGravity(Gravity.TOP, 0, height / 2);
            toastStart.setDuration(Toast.LENGTH_SHORT);
        }

        public Context getContext() {
            return mContext;
        }

        public int getDp(int value) {
            return (int) getContext().getResources().getDisplayMetrics().scaledDensity * value;
        }

        public void show(String prompt) {
            // root
            FrameLayout root = new FrameLayout(getContext()) {
                private Paint mPaint = new Paint();

                @Override
                protected void dispatchDraw(Canvas canvas) {
                    mPaint.setAntiAlias(true);
                    mPaint.setColor(0xCCBEBEBF);
                    float radius = getHeight() / 2;
                    canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, mPaint);
                    super.dispatchDraw(canvas);
                }
            };
            // text
            TextView promptView = new TextView(getContext());
            promptView.setText(prompt);
            promptView.setSingleLine(true);
            promptView.setTextColor(Color.BLACK);
            promptView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            int padding = getDp(10);
            promptView.setPadding(padding * 5, padding, padding * 5, padding);
            root.addView(promptView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            toastStart.setView(root);
            toastStart.show();
        }
    }


}