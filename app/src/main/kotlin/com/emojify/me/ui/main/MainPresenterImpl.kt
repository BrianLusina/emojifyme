package com.emojify.me.ui.main

import android.graphics.Bitmap
import android.net.Uri
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
constructor(val dataManager: DataManager, val schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable): MainPresenter<V>, BasePresenterImpl<V>(dataManager, schedulerProvider, compositeDisposable){

    // fixme: make key unique
    private val sharedPrefsKey = "emojifyme"

    override fun onAttach(baseView: V) {
        super.onAttach(baseView)
        baseView.setupViewListeners()
    }

    override fun onEmojifyMeBtnClicked() {
        baseView.emojifyMe()
    }

    override fun onClearBtnClicked() {
        val photoPath = dataManager.getImageFilePath(sharedPrefsKey)
        dataManager.deleteImageFile(photoPath)
        baseView.clearImage()
    }

    override fun onSaveBtnClicked(bitmap: Bitmap?) {
        val photoPath = dataManager.getImageFilePath(sharedPrefsKey)

        dataManager.deleteImageFile(photoPath)

        dataManager.saveImageFile(bitmap)
    }

    override fun onShareBtnClicked(bitmap: Bitmap?) {
        val photoPath = dataManager.getImageFilePath(sharedPrefsKey)

        dataManager.deleteImageFile(photoPath)

        val savedPhotoLocation = dataManager.saveImageFile(bitmap)

        baseView.shareImage(photoPath, savedPhotoLocation)
    }

    override fun onPermissionDenied() {
        baseView.displayPermissionRationale()
    }

    override fun onPermissionsGranted() {
        baseView.launchCamera()
    }

    override fun onResamplePicRequest() {
        val photoPath = dataManager.getImageFilePath(sharedPrefsKey)
        baseView.resamplePic(photoPath)
    }

    override fun onActivityResultFailed() {
        val photoPath = dataManager.getImageFilePath(sharedPrefsKey)
        dataManager.deleteImageFile(photoPath)
    }

    override fun onActivityResultSuccess() {
        baseView.processAndSetImage()
    }

    override fun onTakePicture() : Uri?{
        val (tempImageFile, photoUri)= dataManager.createTempImageFile()
        tempImageFile?.absolutePath?.let { dataManager.saveImageFilePath(sharedPrefsKey, it) }
        return photoUri
    }
}