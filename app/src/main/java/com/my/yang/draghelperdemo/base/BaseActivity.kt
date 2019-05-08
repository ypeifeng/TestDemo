package com.my.yang.draghelperdemo.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        initData()
        initView()
        initEvent()
        postData()
    }
    abstract fun getLayout():Int

    abstract fun initData()

    abstract fun initView()

    abstract fun initEvent()

    abstract fun postData()


}
