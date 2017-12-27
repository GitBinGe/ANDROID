package com.bg.library.Utils.VerCtrl;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;

import com.bg.library.Base.os.SystemInfo;
import com.bg.library.Utils.Log.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * author: LooooG
 * created on: 2017/12/19 15:11
 * description: 版本控制工具
 */
public class VerCtrlUtils {

    private static VerCtrlUtils sVerCtrlUtils;

    /**
     * get Instance
     *
     * @return
     */
    public static VerCtrlUtils get() {
        if (sVerCtrlUtils == null) {
            sVerCtrlUtils = new VerCtrlUtils();
        }
        return sVerCtrlUtils;
    }


    private Handler mHandler;
    private ContentObserver mObserver;
    private BroadcastReceiver mReceiver;
    private long mDownloadId;
    private DownloadManager mDownloadManager;
    private VersionDownloadListener mListener;
    private String mAuthrity;

    /**
     * Constructor
     */
    public VerCtrlUtils() {
        mAuthrity = SystemInfo.PackageName + ".fileprovider";
        mHandler = new Handler(Looper.getMainLooper());
        // 下载完成广播接收
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                LogUtils.d("VersionControl >> onReceive id: " + id + ", mDownloadId: " + mDownloadId);
                if (mDownloadId == id) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(id);
                    Cursor c = mDownloadManager.query(query);
                    if (c.moveToFirst()) {
                        // 获取文件下载路径
                        int fileUriIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                        String fileUri = c.getString(fileUriIdx);
                        String apkPath = "";
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            if (fileUri != null) {
                                apkPath = Uri.parse(fileUri).getPath();
                            }
                        } else {
                            //Android 7.0以上的方式：请求获取写入权限，这一步报错
                            //过时的方式：DownloadManager.COLUMN_LOCAL_FILENAME
                            int fileNameIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                            apkPath = c.getString(fileNameIdx);
                        }
                        if (!apkPath.isEmpty()) {
                            if (mListener != null) {
                                mListener.onSuccess(apkPath);
                            }
                            LogUtils.d("VersionControl >> onSuccess");
                            // 默认自动安装
                            startInstall(context, new File(apkPath));
                        } else {
                            if (mListener != null) {
                                mListener.onFailed();
                            }
                            LogUtils.d("VersionControl >> onFailed");
                        }
                    }
                    if (c != null) {
                        c.close();
                    }
                    // 注销下载完成广播接收
                    context.unregisterReceiver(mReceiver);
                    // 注销内容观察者
                    context.getContentResolver().unregisterContentObserver(mObserver);
                    // 注销下载状态回调
                    setVersionDownloadListener(null);
                }
            }
        };
        // 内容观察者
        mObserver = new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getProgress(mDownloadId);
                    }
                });
            }
        };
    }

    public void setVersionDownloadListener(VersionDownloadListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 下载更新程序
     *
     * @param context
     * @param url
     * @param showNotification
     * @param authority
     * @return
     */
    public long downloadApk(Context context, String url, boolean showNotification, String authority, VersionDownloadListener listener) {
        mListener = listener;
        mAuthrity = authority;
        return downloadApk(context, url, showNotification);
    }

    /**
     * 下载更新程序
     *
     * @param context
     * @param url
     * @param showNotification
     * @param authority
     * @return
     */
    public long downloadApk(Context context, String url, boolean showNotification, String authority) {
        mAuthrity = authority;
        return downloadApk(context, url, showNotification);
    }

    /**
     * 下载更新程序
     *
     * @param context
     * @param url
     * @param showNotification
     * @return
     */
    public long downloadApk(Context context, String url, boolean showNotification) {
        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setVisibleInDownloadsUi(true);
        request.setTitle(SystemInfo.AppName);
        request.setNotificationVisibility(showNotification ?
                DownloadManager.Request.VISIBILITY_VISIBLE :
                DownloadManager.Request.VISIBILITY_HIDDEN);
        String name = url.substring(url.lastIndexOf("/") + 1);
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
        if (file.exists()) {
            if (mListener != null) {
                mListener.onSuccess(file.getPath());
            }
            startInstall(context, file);
            return 0;
        }
        request.setDestinationUri(Uri.fromFile(file));
        mDownloadId = mDownloadManager.enqueue(request);
        LogUtils.d("VersionControl >> 开始下载");
        if (mListener != null) {
            mListener.onStart(file.getPath());
        }
        // 注册内容观察者
        context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"),
                true, mObserver);
        // 注册下载完成广播接收
        context.getApplicationContext().registerReceiver(mReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return mDownloadId;
    }


    /**
     * 获取进度信息
     * @param downloadId 要获取下载的id
     * @return 进度信息 max-100
     */
    public int getProgress(long downloadId) {
        // 查询进度
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        int progress = 0;
        try {
            cursor = mDownloadManager.query(query);//获得游标
            if (cursor != null && cursor.moveToFirst()) {
                // 当前的下载量
                int downloadBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                // 文件总大小
                int totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                if (mListener != null) {
                    mListener.onProgress(totalBytes, downloadBytes);
                }
                progress = downloadBytes * 100/ totalBytes;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return progress;
    }

    /**
     * 提升读写权限
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    private void setPermission(String filePath) {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用系统安装界面
     *
     * @param context
     * @param file
     */
    public void startInstall(Context context, File file) {
        setPermission(file.getPath());
        Intent intent = new Intent("android.intent.action.VIEW");
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(getUriForFile(context, file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 兼容7.0手机系统，获取共享文件URI
     *
     * @param context
     * @param file
     * @return
     */
    private Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), mAuthrity, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 版本下载状态监听器
     */
    public interface VersionDownloadListener {
        void onStart(String apkPath);
        void onSuccess(String apkPath);
        void onProgress(int totalBytes, int downloadSoFar);
        void onFailed();
    }
}
