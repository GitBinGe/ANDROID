package com.bg.library.Utils.Image;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.bg.library.Utils.Log.LogUtils;

/**
 * Created by BinGe on 2018/1/9.
 * 用于缓存bitmap
 */

public class ImageCache {

    private static ImageCache sCache;

    public static ImageCache get() {
        if (sCache == null) {
            sCache = new ImageCache();
        }
        return sCache;
    }

    private LruCache<String, Bitmap> lruCache;

    private ImageCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        lruCache = new LruCache<String, Bitmap>((int) (maxMemory * 0.2f)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                LogUtils.d("Image size is " + (value.getRowBytes() * value.getHeight() / 1024 / 1024));
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /**
     * 设置最大容量 0-100
     */
    public void setMaxSize(float percent) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() * percent / 100f);
        LogUtils.d("Runtime memory is " + (Runtime.getRuntime().maxMemory() / 1024 / 1024)
                + ", Image Cache max size is " + (maxMemory / 1024 / 1024));
        lruCache.resize(maxMemory);
    }

    public void put(String key, Bitmap bitmap) {
        lruCache.put(key, bitmap);
    }

    public Bitmap get(String key) {
        return lruCache.get(key);
    }

    public void remove(String key) {
        lruCache.remove(key);
    }

}
