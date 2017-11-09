package com.emojify.me.di.components

import android.app.Application
import android.content.Context
import com.emojify.me.app.EmojifyMeApp
import com.emojify.me.data.DataManager
import com.emojify.me.data.io.SchedulerProvider
import com.emojify.me.di.modules.AppModule
import com.emojify.me.di.qualifiers.AppContextQualifier
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
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

    val dataManager: DataManager

    val schedulerProvider: SchedulerProvider

    val compositeDisposable : CompositeDisposable
}