package com.example.hiennv.blockcall.ui.splash

import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.example.hiennv.blockcall.R
import com.example.hiennv.blockcall.base.BaseActivity
import com.example.hiennv.blockcall.ui.home.MainActivity
import com.example.hiennv.blockcall.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    @BindView(R.id.btn_continue)
    lateinit var btnContinue: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun setupToolbar() {
    }

    override fun initData() {
    }

    override fun initEvents() {
        btn_continue.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                handleContinue()
            }

        })
    }

    @OnClick(R.id.btn_continue)
    fun doClick(view: View) {
        when (view.id) {
            //R.id.btn_continue -> handleContinue()
        }
    }

    //Handle permission
    private fun handleContinue() {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG).show()
        if (!PermissionUtils.checkPermissionReadContact(this)
            || !PermissionUtils.checkPermissionReadCallLog(this)
            || !PermissionUtils.checkPermissionCallPhone(this)
        ) {
            PermissionUtils.requestAllPermission(this)
        } else {
            Handler().postDelayed({
                //open main
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }, 2000)
        }
    }
}