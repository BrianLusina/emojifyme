package com.emojify.me.ui.base

import com.emojify.me.data.DataManager
import com.emojify.me.data.io.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Base presenter implementation
 */
open class BasePresenterImpl<V : BaseView>
@Inject
constructor(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider,
       val compositeDisposable: CompositeDisposable): BasePresenter<V> {

    /**
     * Gets the base view
     * @return [BaseView]
     */
    lateinit var baseView: V
        private set

    override fun onAttach(baseView: V) {
        this.baseView = baseView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
    }
    /**
     * Checks if the view has been attached */
    val isViewAttached: Boolean
        get() = baseView != null

    /**
     * Checks if the view has been attached
     * @throws BaseViewNotAttachedException error that is thrown when the view is not attached.
     * *
     */
    fun checkViewAttached() {
        if (!isViewAttached) {
            throw BaseViewNotAttachedException()
        }
    }

    /**
     * Custom runtime exception that is thrown when an a request of data is made to the presenter before
     * attaching the view.
     */
    class BaseViewNotAttachedException : RuntimeException("Please call Presenter.onAttach(BaseView) before requesting data to Presenter")

}