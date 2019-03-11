package com.example.hiennv.blockcall.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.support.localbroadcastmanager.R
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.*
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class PermissionUtils {
    companion object {
        val REQUEST_OPEN_CAMERA: Int = 100
        val REQUEST_OPEN_SETTING: Int = 101

        /**
         *Kiem tra quyen call phone
         * tra ve true neu da dc cap chua tra ve false
         */
        fun checkPermissionCallPhone(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            return true
        }

        /**
         *Kiem tra quyen read call log
         * tra ve true neu da dc cap chua tra ve false
         */
        fun checkPermissionReadCallLog(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_CALL_LOG
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            return true
        }

        /**
         *Kiem tra quyen doc danh ba
         * tra ve true neu da dc cap chua tra ve false
         */
        fun checkPermissionReadContact(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            return true
        }

        /**
         *
         */
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

        /**
         *Request all permission
         */
        fun requestAllPermission(activity: Activity) {
            Dexter.withActivity(activity)
                .withPermissions(
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.READ_CONTACTS
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        //Kiem tra neu tat ca cac quyen duoc cap
                        if (report!!.areAllPermissionsGranted()) {
                            Toast.makeText(activity, "Permissions are granted!", Toast.LENGTH_LONG).show()
                        }

                        //Neu co 1 quyen bi tu choi
                        if (report.isAnyPermissionPermanentlyDenied) {
                            //Show dialog goi den man setting
                            showSettingsDialog(activity)
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }

                })
                .withErrorListener(object : PermissionRequestErrorListener {
                    override fun onError(error: DexterError?) {
                        Toast.makeText(activity, "Error: " + error!!.name, Toast.LENGTH_LONG).show()
                    }
                })
                .onSameThread()
                .check()
        }

        /**
         * Yeu cau cap quyen callphone
         */
        fun requestPermissionCallPhone(activity: Activity) {
            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        //Neu dc cap quyen
                        openCamera(activity)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        if (response!!.isPermanentlyDenied) {
                            showSettingsDialog(activity)
                        }
                    }

                }).check()
        }

        /**
         * Yeu cau quyen readcontact
         */
        fun requestPermissionReadContact(activity: Activity) {
            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        //Neu dc cap quyen
                        //openCamera(activity)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        if (response!!.isPermanentlyDenied) {
                            showSettingsDialog(activity)
                        }
                    }

                }).check()
        }

        /**
         * Yeu cau cap quyen readcallLog
         */
        fun requestPermissionCallLog(activity: Activity) {
            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.READ_CALL_LOG)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        //Neu dc cap quyen
                        //openCamera(activity)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        if (response!!.isPermanentlyDenied) {
                            showSettingsDialog(activity)
                        }
                    }

                }).check()
        }

        /**
         * Show setting dialog
         */
        private fun showSettingsDialog(activity: Activity) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle("Need permissions")
            builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
            builder.setPositiveButton("GOTO SETTINGS", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.cancel()
                    openSetting(activity)
                }

            })
            builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.cancel()
                }
            })
            builder.create().show()
        }

        /**
         * Open setting
         */
        private fun openSetting(activity: Activity) {
            val intent: Intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
            val uri: Uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivityForResult(intent, REQUEST_OPEN_SETTING)
        }

        /**
         *Request permission camera
         */
        fun requestCameraPermission(activity: Activity) {
            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        //Neu dc cap quyen
                        openCamera(activity)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        if (response!!.isPermanentlyDenied) {
                            showSettingsDialog(activity)
                        }
                    }

                }).check()
        }

        /**
         * Open camera
         */
        private fun openCamera(activity: Activity) {
            val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(intent, REQUEST_OPEN_CAMERA)
        }

        /**
         * Yeu cau cap quyen co ten la permissionName
         */
        fun requestPermission(activity: Activity, permissionName: String) {
            Dexter.withActivity(activity)
                .withPermission(permissionName)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        //Neu dc cap quyen
                        //openCamera(activity)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        if (response!!.isPermanentlyDenied) {
                            showSettingsDialog(activity)
                        }
                    }

                }).check()
        }
    }
}