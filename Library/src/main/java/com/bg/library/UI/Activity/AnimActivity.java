package com.bg.library.UI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


import com.bg.library.R;

import java.util.Stack;

/**
 * Created by BinGe on 2017/9/29.
 * 主要是添加Activity进入和退出的动画，匹配现有两种动画
 */

public class AnimActivity extends AppCompatActivity {

    private static int[][] ANIMS = new int[][]{
            {
                    R.anim.activity_push_open_in,
                    R.anim.activity_push_open_out,
                    R.anim.activity_push_close_in,
                    R.anim.activity_push_close_out
            },
            {
                    R.anim.activity_presentation_open_in,
                    R.anim.activity_presentation_open_out,
                    R.anim.activity_presentation_close_in,
                    R.anim.activity_presentation_close_out
            },
            {
                    R.anim.activity_alpha_in,
                    R.anim.activity_presentation_open_out,
                    R.anim.activity_alpha_out,
                    R.anim.activity_presentation_close_out
            }
    };

    private static Stack<Integer[]> stack = new Stack<>();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        if (!stack.isEmpty()) {
            Integer[] last = stack.pop();
            int enterAnim = last[0];
            int exitAnim = last[1];
            if (enterAnim > 0 || exitAnim > 0) {
                for (int[] anim : ANIMS) {
                    if (anim[0] == enterAnim && anim[1] == exitAnim) {
                        overridePendingTransition(anim[2], anim[3]);
                    }
                }
            }
        }
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
        for (int[] anim : ANIMS) {
            if (anim[0] == enterAnim && anim[1] == exitAnim) {
                stack.push(new Integer[]{enterAnim, exitAnim});
            }
        }
    }

}
