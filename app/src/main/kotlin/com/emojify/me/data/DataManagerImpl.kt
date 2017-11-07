package com.emojify.me.data

import android.graphics.Bitmap
import com.emojify.me.data.file.FileHelper
import com.emojify.me.data.prefs.SharedPrefsHelper
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 06/11/17.
 * @Notes implementation for data manager
 */
@Singleton
class DataManagerImpl @Inject constructor(
        private val fileHelper: FileHelper, private val sharedPrefsHelper: SharedPrefsHelper): DataManager{

    override fun deleteImageFile(photoPath: String): Boolean {
        return fileHelper.deleteImageFile(photoPath)
    }

    override fun saveImageFile(mResultsBitmap: Bitmap): String? {
        return fileHelper.saveImageFile(mResultsBitmap)
    }

    override fun createTempImageFile(): File? {
        return fileHelper.createTempImageFile()
    }

    // shared prefs
    override fun saveImageFilePath(imageFileKey: String, imageFilePath: String) {
        sharedPrefsHelper.saveImageFilePath(imageFileKey, imageFilePath)
    }

    override fun getImageFilePath(imageFileKey: String): String {
        return sharedPrefsHelper.getImageFilePath(imageFileKey)
    }
}