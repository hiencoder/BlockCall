package com.example.hiennv.blockcall.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        ButterKnife.bind(this)
        setupToolbar()
        initData()
        initEvents()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun setupToolbar()

    protected abstract fun initData()

    protected abstract fun initEvents()
}