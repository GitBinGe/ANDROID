package com.bg.library.UI.Dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.bg.library.UI.Animation.ValueAnimation;

/**
 * Progress
 */
public class Progress {

    private static ProgressDialog dialog;

    public static void show(Context context) {
        dismiss();
        dialog = new ProgressDialog(context);
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    static class ProgressDialog extends BaseDialog {
        public ProgressDialog(@NonNull Context context) {
            super(context, true);
        }

        public void show() {
            dismiss();

            FrameLayout root = new FrameLayout(getContext()) {
                private Paint mPaint = new Paint();

                @Override
                protected void dispatchDraw(Canvas canvas) {
                    mPaint.setAntiAlias(true);
                    float radius = getDp(10);
                    canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, mPaint);
                    super.dispatchDraw(canvas);
                }
            };
            int width = (int) getDp(80);
            Loading loading = new Loading(getContext());
            root.addView(loading, width, width);
            setContentView(root);
            super.show();
        }

        class Loading extends View {

            private RectF r = new RectF(0, 0, getDp(40), getDp(40));
            private Paint mPaint = new Paint();
            private ValueAnimation animator;

            public Loading(Context context) {
                super(context);
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setStrokeWidth(getDp(1));
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(Color.WHITE);

                animator = new ValueAnimation(1000);
                animator.setRepeatCount(-1);
                animator.start();
            }

            @Override
            protected void onDraw(Canvas canvas) {
                r.offsetTo(getWidth() / 2 - r.width() / 2, getHeight() / 2 - r.height() / 2);
                canvas.drawArc(r, animator.getValue() * 359, 320, false, mPaint);
                invalidate();
            }


        }
    }


}