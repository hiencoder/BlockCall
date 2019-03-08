package com.example.hiennv.blockcall.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun setupToolbar()

    protected abstract fun initData()

    protected abstract fun initEvents()
}