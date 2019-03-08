package com.example.hiennv.blockcall.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

class PermissionUtils {
    companion object {
        fun checkPermissionPhoneStateGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= 23
                && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            return true
        }

        fun checkPermissionStorageGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= 23
                && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            return true
        }
    }
}