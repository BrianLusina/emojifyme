package com.emojify.me.di.modules

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.emojify.me.di.scopes.ActivityScope
import com.emojify.me.ui.main.MainPresenter
import com.emojify.me.ui.main.MainPresenterImpl
import com.emojify.me.ui.main.MainView
import dagger.Module
import dagger.Provides

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Activity Module
 */
@Module
class ActivityModule (val mActivity: AppCompatActivity){

    @Provides
    @ActivityScope
    fun provideContext(): Context {
        return mActivity
    }

    @Provides
    fun provideActivity(): AppCompatActivity {
        return mActivity
    }

    @Provides
    @ActivityScope
    fun provideMainPresenter(mainPresenter: MainPresenterImpl<MainView>): MainPresenter<MainView> {
        return mainPresenter
    }
}