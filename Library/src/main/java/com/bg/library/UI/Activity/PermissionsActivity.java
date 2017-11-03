package com.bg.library.UI.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.bg.library.Utils.Log.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LooooG on 2017/10/12.
 * 权限管理
 */
public class PermissionsActivity extends SoftInputActivity {

    public static class Permissions {
        /**
         *   permission group : PHONE
         * 	READ_PHONE_STATE
         *   CALL_PHONE
         *   READ_CALL_LOG
         *   WRITE_CALL_LOG
         *   ADD_VOICEMAIL
         *   USE_SIP
         *   PROCESS_OUTGOING_CALLS
         */
        public static final String PHONE= Manifest.permission.READ_PHONE_STATE;

        /**
         *  permission group : CALENDAR
         *  READ_CALENDAR
         *  WRITE_CALENDAR
         */
        public static final String CALENDAR= Manifest.permission.READ_CALENDAR;

        /**
         *  permission group : CAMERA
         *  CAMERA
         */
        public static final String CAMERA= Manifest.permission.CAMERA;

        /**
         *  permission group : CONTACTS
         *  READ_CONTACTS
         *  WRITE_CONTACTS
         *  GET_ACCOUNTS
         */
        public static final String CONTACTS= Manifest.permission.READ_CONTACTS;

        /**
         *  permission group : LOCATION
         *  ACCESS_FINE_LOCATION
         *  ACCESS_COARSE_LOCATION
         */
        public static final String LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;

        /**
         *  permission group : MICROPHONE
         *  RECORD_AUDIO
         */
        public static final String MICROPHONE= Manifest.permission.RECORD_AUDIO;

        /**
         *  permission group : SENSORS
         *  BODY_SENSORS
         */
        public static final String SENSORS= Manifest.permission.BODY_SENSORS;

        /**
         *  permission group : SMS
         *  SEND_SMS
         *  RECEIVE_SMS
         *  READ_SMS
         *  RECEIVE_WAP_PUSH
         *  RECEIVE_MMS
         */
        public static final String SMS= Manifest.permission.SEND_SMS;

        /**
         *  permission group : STORAGE
         *  READ_EXTERNAL_STORAGE
         *  WRITE_EXTERNAL_STORAGE
         */
        public static final String STORAGE= Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

    

    private Map<String, PermissionCallback> mRequestPermissionCache = new HashMap<>();

    /**
     * 判断是否有这个权限
     * @param permission
     * @return
     */
    public boolean isHasPermission(String permission) {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, permission);
    }

    /**
     * 申请权限
     * @param permission
     * @param callback
     */
    public void requestPermission(String permission, PermissionCallback callback) {
        if (isHasPermission(permission)) {
            if (callback != null) {
                callback.onPermissionCallback(permission, true);
            }
        } else {
            if (callback != null) {
                mRequestPermissionCache.put(permission, callback);
            }
            ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }

    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (String permission : permissions) {
            PermissionCallback callback = mRequestPermissionCache.get(permission);
            if (callback != null) {
                callback.onPermissionCallback(permission, isHasPermission(permission));
            }
            mRequestPermissionCache.remove(permission);
        }
    }

    /**
     *  申请权限的回调
     */
    public interface PermissionCallback {
        void onPermissionCallback(String permission, boolean isPermissionGranted);
    }
}
