package com.bg.library.UI.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bg.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/10/26.
 */

public class BaseDialog extends Dialog {

    private static List<Dialog> dialogQueue = new ArrayList<>();

    private static Dialog sLastDialog;

    public BaseDialog(@NonNull Context context) {
        this(context, false);
    }

    public BaseDialog(@NonNull Context context, boolean translucent) {
        super(context, translucent ? R.style.dialog_translucent : R.style.share_dialog);
    }

    public DisplayMetrics getDisplayMetrics() {
        return getContext().getResources().getDisplayMetrics();
    }

    public float getDp(int value) {
        return getDisplayMetrics().scaledDensity * value;
    }

    @Override
    public void show() {
        if (sLastDialog != null) {
            sLastDialog.dismiss();
            sLastDialog = null;
        }
        super.show();
    }

}
