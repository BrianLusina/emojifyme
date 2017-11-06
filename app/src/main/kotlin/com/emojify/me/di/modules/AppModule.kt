package com.emojify.me.di.modules

import android.app.Application
import android.content.Context
import com.emojify.me.data.DataManager
import com.emojify.me.data.DataManagerImpl
import com.emojify.me.data.file.FileHelper
import com.emojify.me.data.file.FileHelperImpl
import com.emojify.me.data.io.SchedulerProvider
import com.emojify.me.data.io.SchedulerProviderImpl
import com.emojify.me.di.qualifiers.AppContextQualifier
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

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


    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideSchedulers(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    @Provides
    @Singleton
    fun provideDataManager(dataManager: DataManagerImpl): DataManager {
        return dataManager
    }

    @Provides
    @Singleton
    fun provideFileHelper(fileHelper: FileHelperImpl): FileHelper {
        return fileHelper
    }
}