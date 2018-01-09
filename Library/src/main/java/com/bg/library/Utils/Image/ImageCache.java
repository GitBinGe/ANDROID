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
    private int currentSize = 0;

    private ImageCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        lruCache = new LruCache<String, Bitmap>((int) (maxMemory * 0.2f)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int size = value.getRowBytes() * value.getHeight();
                return size;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                int size = oldValue.getRowBytes() * oldValue.getHeight();
                currentSize -= size;
                LogUtils.d("ImageCache :Current size is " + (currentSize / 1024f) + "KB, " +
                        "Removed image size is " + (size / 1024f + "KB") + "-->" + key);
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
    }

    /**
     * 设置最大容量 0-100
     */
    public void setMaxSize(float percent) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() * percent / 100f);
        LogUtils.d("ImageCache : Runtime memory is " + (Runtime.getRuntime().maxMemory() / 1024f)
                + "KB, Image Cache max size is " + (maxMemory / 1024f) + "KB");
        lruCache.resize(maxMemory);
    }

    public void put(String key, Bitmap bitmap) {
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        currentSize += size;
        LogUtils.d("ImageCache :Current size is " + (currentSize / 1024f) + "KB, " +
                "Put image size is " + (size / 1024f + "KB") + "-->" + key);
        lruCache.put(key, bitmap);
    }

    public Bitmap get(String key) {
        return lruCache.get(key);
    }

    public void remove(String key) {
        lruCache.remove(key);
    }

}
