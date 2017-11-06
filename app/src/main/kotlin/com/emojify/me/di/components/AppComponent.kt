package com.emojify.me.di.components

import android.app.Application
import android.content.Context
import com.emojify.me.app.EmojifyMeApp
import com.emojify.me.di.modules.AppModule
import com.emojify.me.di.qualifiers.AppContextQualifier
import dagger.Component
import javax.inject.Singleton

/**
 * @author lusinabrian on 06/11/17.
 * @Notes App component
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun injectApp(emojifyMeApp: EmojifyMeApp)

    @AppContextQualifier
    fun context(): Context

    val getApplication: Application
}