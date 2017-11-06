package com.emojify.me.ui.main

import com.emojify.me.data.DataManager
import com.emojify.me.data.io.SchedulerProvider
import com.emojify.me.ui.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Main presenter implementation
 */
class MainPresenterImpl<V : MainView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable): MainPresenter<V>, BasePresenterImpl<V>(dataManager, schedulerProvider, compositeDisposable){

}