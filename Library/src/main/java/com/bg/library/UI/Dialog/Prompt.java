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

/**
 * Progress
 */
public class Prompt {

    private static PromptDialog dialog;

    public static void show(Context context) {
        dismiss();
        dialog = new PromptDialog(context);
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    static class PromptDialog extends BaseDialog {
        public PromptDialog(@NonNull Context context) {
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
            int width = getDp(80);
            PromptView loading = new PromptView(getContext());
            root.addView(loading, width, width);
            setContentView(root);
            super.show();
        }

        class PromptView extends View {

            private RectF r = new RectF(0, 0, getDp(40), getDp(40));
            private Paint mPaint = new Paint();
            private ValueAnimator animator;

            public PromptView(Context context) {
                super(context);
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setStrokeWidth(getDp(1));
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(Color.WHITE);

                animator = ValueAnimator.ofInt(0, 359);
                animator.setDuration(1000);
                animator.setRepeatCount(-1);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
            }

            @Override
            protected void onDraw(Canvas canvas) {
                r.offsetTo(getWidth() / 2 - r.width() / 2, getHeight() / 2 - r.height() / 2);
                canvas.drawArc(r, (int) animator.getAnimatedValue(), 320, false, mPaint);
                invalidate();
            }


        }
    }


}