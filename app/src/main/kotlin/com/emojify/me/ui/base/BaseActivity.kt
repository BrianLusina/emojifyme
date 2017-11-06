package com.emojify.me.ui.base

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.emojify.me.app.EmojifyMeApp
import com.emojify.me.di.components.ActivityComponent
import com.emojify.me.di.components.DaggerActivityComponent

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Base Activity
 */
abstract class BaseActivity : AppCompatActivity(), BaseView{
    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    val activityComponent : ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .appComponent((application as EmojifyMeApp).appComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }


}