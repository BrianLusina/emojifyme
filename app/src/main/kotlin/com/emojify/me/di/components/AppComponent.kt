package com.emojify.me.di.components

import com.emojify.me.di.modules.AppModule
import dagger.Component

/**
 * @author lusinabrian on 06/11/17.
 * @Notes App component
 */
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
}