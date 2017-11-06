package com.emojify.me.di.components

import com.emojify.me.di.modules.ActivityModule
import dagger.Component

/**
 * @author lusinabrian on 06/11/17.
 * @Notes
 */
@Component(modules = arrayOf(ActivityModule::class), dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {
}