package com.bg.lib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bg.library.UI.Activity.PresenterActivity;
import com.bg.library.Utils.Log.LogUtils;

public class MainActivity extends PresenterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission(Permissions.CAMERA, new PermissionCallback() {
            @Override
            public void onPermissionCallback(String permission, boolean isPermissionGranted) {
                LogUtils.d("permission : " + permission + "-->" + isPermissionGranted);
                doSomething();
            }
        });
        requestPermission(Permissions.STORAGE, new PermissionCallback() {
            @Override
            public void onPermissionCallback(String permission, boolean isPermissionGranted) {
                LogUtils.d("permission : " + permission + "-->" + isPermissionGranted);
                doSomething();
            }
        });
    }

    private void doSomething() {
        LogUtils.d("permission : doSomething");
    }

}
