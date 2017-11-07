package com.emojify.me.ui.main

import com.emojify.me.ui.base.BaseView

/**
 * @author lusinabrian on 06/11/17.
 * @Notes MainView that in
 */
interface MainView : BaseView{

    fun setupViewListeners()

    fun emojifyMe()

    fun launchCamera()

    /**
     * Displays permission rationale if the user denies permission
     * */
    fun displayPermissionRationale()

    fun processAndSetImage()

    fun clearImage()

    /**
     * Resamples the given picture given the photo path
     * @param photoPath
     * */
    fun resamplePic(photoPath: String)
}