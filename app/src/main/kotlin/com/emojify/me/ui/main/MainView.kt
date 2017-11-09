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
     * Shares image
     * @param photoPath
     * @param savedPhotoLocation Saved photo location on device
     * */
    fun shareImage(photoPath: String, savedPhotoLocation: String?)

    /**
     * Notify user of saved photo location
     * @param savedPhotoLocation
     * */
    fun notifyUserOfSavedImage(savedPhotoLocation: String?)

    /**
     * Displays permission rationale if the user denies permission
     * */
    fun displayPermissionRationale()

    fun processAndSetImage()

    /**
     * Clears views and image from View
     * @param isFileDeleted Whether the file was successfully deleted
     * */
    fun clearImage(isFileDeleted: Boolean)

    /**
     * Resamples the given picture given the photo path
     * @param photoPath
     * */
    fun resamplePic(photoPath: String)
}