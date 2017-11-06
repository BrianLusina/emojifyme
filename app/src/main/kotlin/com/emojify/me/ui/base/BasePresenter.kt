package com.emojify.me.ui.base

/**
 * @author lusinabrian on 06/11/17.
 * @Notes BasePresenter from which all presenters will inherit from
 */
interface BasePresenter<V : BaseView> {

    fun onAttach(baseView : V)

    fun onDetach()
}