package com.emojify.me.ui.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Base Activity
 */
abstract class BaseActivity : AppCompatActivity(), BaseView{
    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}