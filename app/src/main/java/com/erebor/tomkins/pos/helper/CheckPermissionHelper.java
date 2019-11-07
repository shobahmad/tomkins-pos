package com.erebor.tomkins.pos.helper;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by ryandzhunter on 07/03/18.
 */

public class CheckPermissionHelper {

    private Activity activity;

    public CheckPermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public boolean checkCameraPermission(){
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkCameraAndExternalStoragePermission(){
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void showCameraPermission(int code){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                code);
    }

    public void showCameraAndExternalPermission(int code){
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                code);
    }

}
