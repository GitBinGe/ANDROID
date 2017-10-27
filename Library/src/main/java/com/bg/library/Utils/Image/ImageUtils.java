package com.bg.library.Utils.Image;

import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by BinGe on 2017/10/27.
 */

public class ImageUtils {

    /**
     * 检验图片是否正常
     * @param path
     * @return
     */
    public static boolean checkImage(String path) {
        if (new File(path).exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            if (options.outWidth > 0 && options.outHeight > 0) {
                return true;
            }
        }
        return false;
    }

}
