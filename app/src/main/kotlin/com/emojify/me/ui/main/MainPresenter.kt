package com.emojify.me.ui.main

import android.graphics.Bitmap
import android.net.Uri
import com.emojify.me.ui.base.BasePresenter

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Main Presenter
 */
interface MainPresenter<V : MainView> : BasePresenter<V>{

    fun onEmojifyMeBtnClicked()

    fun onClearBtnClicked()

    /**
     * Saves the given image
     * */
    fun onSaveBtnClicked(bitmap: Bitmap?)

    /**
     * Shares the given image
     * @param bitmap
     * */
    fun onShareBtnClicked(bitmap: Bitmap?)

    fun onPermissionsGranted()

    fun onPermissionDenied()

    fun onTakePicture() : Uri?

    /**
     * On Activity result success
     * */
    fun onActivityResultSuccess()

    /**
     * On Activity result failed
     * */
    fun onActivityResultFailed()

    /**
     * Request to resample picture
     * */
    fun onResamplePicRequest()
}