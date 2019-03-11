package com.example.hiennv.blockcall.ui.splash

import android.view.View
import android.widget.Toast
import butterknife.OnClick
import com.example.hiennv.blockcall.R
import com.example.hiennv.blockcall.base.BaseActivity
import com.example.hiennv.blockcall.utils.PermissionUtils
import com.karumi.dexter.Dexter

class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun setupToolbar() {
    }

    override fun initData() {
    }

    override fun initEvents() {
    }

    @OnClick(R.id.btn_continue)
    fun doClick(view: View) {
        when (view.id) {
            R.id.btn_continue -> handleContinue()
        }
    }

    //Handle permission
    private fun handleContinue() {
        Toast.makeText(this,"Click",Toast.LENGTH_LONG).show()
        /*if (!PermissionUtils.checkPermissionCallPhone(this)) {
            if (PermissionUtils.checkPermissionReadCallLog(this)) {
                if (PermissionUtils.checkPermissionReadContact(this)) {
                    //Neu chi chua cap quyen call phone
                    PermissionUtils.requestPermissionCallPhone(this)
                } else {
                    //Neu chua cap quyen callphone va readcontact

                }
            } else {
                //Chua cap quyen callphone va
            }
        }*/
        if (!PermissionUtils.checkPermissionReadContact(this)
            || !PermissionUtils.checkPermissionReadCallLog(this)
            || !PermissionUtils.checkPermissionCallPhone(this)
        ) {
            PermissionUtils.requestAllPermission(this)
        }
    }
}