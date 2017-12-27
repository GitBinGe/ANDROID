package com.bg.library.Base.os;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import java.io.File;

/**
 * Created by BinGe on 2017/9/13.
 * 设备的信息
 */

public class SystemInfo {

    /**
     * App名称
     */
    public static String AppName;

    /**
     * App文件路径
     */
    public static String AppFilePath;

    /**
     * SD卡文件路径
     */
    public static String SDCardFilePath;

    /**
     * 存图片的路径
     */
    public static String ImageCacheDir;

    /**
     * 版本号
     */
    public static int VersionCode = 1;

    /**
     * 版本名
     */
    public static String VersionName = "1.0";

    /**
     * 包名
     */
    public static String PackageName = "com.*";

    /**
     * Android ID
     */
    public static String AndroidID = "androidID";

    /**
     * 屏幕相关信息
     */
    public static Screen Screen;

    /**
     * 使用前需要调用此函数初始化数据
     *
     * @param context
     */
    public static void initialize(Context context) {
        AppName = getAppName(context);
        AppFilePath = getAppFilesPath(context);
        SDCardFilePath = getSDCardFilesPath(context);
        ImageCacheDir = getImageCacheDir(context);
        VersionCode = getVersionCode(context);
        VersionName = getVersionName(context);
        PackageName = context.getPackageName();
        AndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Screen = new Screen(context);

        Log.d("SystemInfo",
                "\n" + "Android  ID : " + AndroidID +
                        "\n" + "VersionName : " + VersionName +
                        "\n" + "VersionCode : " + VersionCode +
                        "\n"
        );
    }

    private static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private static String getVersionName(Context context) {
        String versionName = "v1.0";
        try {
            versionName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            int labelRes = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取App文件路径
     *
     * @return
     */
    private static String getAppFilesPath(Context context) {
        String path = context.getFilesDir().getAbsolutePath();
        return path;
    }

    /**
     * 获取App文件路径
     *
     * @return
     */
    private static String getSDCardFilesPath(Context context) {
        String path = "/sdcard/" + context.getPackageName() + "/files/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取App文件路径
     *
     * @return
     */
    private static String getImageCacheDir(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + "ImageCache/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

}
