package com.erebor.tomkins.pos.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Created by ryandzhunter on 07/03/18.
 */

class CheckPermissionHelper(private val activity: Activity) {

    fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun checkCameraAndExternalStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun showCameraPermission(code: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),
                code)
    }

    fun showCameraAndExternalPermission(code: Int) {
        ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                code)
    }

}
