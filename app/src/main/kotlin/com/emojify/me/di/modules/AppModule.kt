package com.emojify.me.di.modules

import android.app.Application
import android.content.Context
import com.emojify.me.di.qualifiers.AppContextQualifier
import dagger.Module
import dagger.Provides

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Application Module
 */
@Module
class AppModule(val mApplication: Application){

    @Provides
    @AppContextQualifier
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }
}