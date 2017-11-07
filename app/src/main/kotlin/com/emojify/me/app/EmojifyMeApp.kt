package com.emojify.me.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import com.emojify.me.di.components.AppComponent
import com.emojify.me.di.components.DaggerAppComponent
import com.emojify.me.di.modules.AppModule

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Application
 */
class EmojifyMeApp : Application(){
    val appComponent : AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.injectApp(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}