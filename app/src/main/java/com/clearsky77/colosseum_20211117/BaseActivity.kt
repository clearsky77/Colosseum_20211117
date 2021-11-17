package com.clearsky77.colosseum_20211117

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity() : AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()
}