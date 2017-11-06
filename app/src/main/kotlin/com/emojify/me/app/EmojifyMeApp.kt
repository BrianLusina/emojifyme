package com.emojify.me.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Application
 */
class EmojifyMeApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}