package com.emojify.me.di.components

import com.emojify.me.di.modules.ActivityModule
import com.emojify.me.di.scopes.ActivityScope
import com.emojify.me.ui.main.MainActivity
import dagger.Component

/**
 * @author lusinabrian on 06/11/17.
 * @Notes
 */
@ActivityScope
@Component(modules = arrayOf(ActivityModule::class), dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {

    fun injectMain(mainActivity: MainActivity)

}