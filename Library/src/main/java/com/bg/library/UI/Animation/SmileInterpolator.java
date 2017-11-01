package com.bg.library.UI.Animation;

import android.view.animation.Interpolator;

/**
 * Created by LooooG on 2017/11/1.
 *
 */
public class SmileInterpolator implements Interpolator {

    public SmileInterpolator() {
    }

    public float getInterpolation(float input) {
        return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }
}
