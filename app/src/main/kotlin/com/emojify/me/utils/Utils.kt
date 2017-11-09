@file:JvmName("Utils")
package com.emojify.me.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.content.FileProvider
import android.util.DisplayMetrics
import android.view.WindowManager
import java.io.File


/**
 * Utility to share image
 * @param context application context
 * @param imagePath Path to image
 * */
fun shareImageUtil(context: Context, imagePath: String) {
    // Create the share intent and start the share activity
    val imageFile = File(imagePath)
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "image/*"
    val photoURI = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, imageFile)
    shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI)
    context.startActivity(shareIntent)
}


/**
 * Resamples the captured photo to fit the screen for better memory usage.
 *
 * @param context   The application context.
 * @param imagePath The path of the photo to be resampled.
 * @return The resampled bitmap
 */
fun resamplePicUtil(context: Context, imagePath: String): Bitmap {

    // Get device screen size information
    val metrics = DisplayMetrics()
    val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    manager.defaultDisplay.getMetrics(metrics)

    val targetH = metrics.heightPixels
    val targetW = metrics.widthPixels

    // Get the dimensions of the original bitmap
    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(imagePath, bmOptions)
    val photoW = bmOptions.outWidth
    val photoH = bmOptions.outHeight

    // Determine how much to scale down the image
    val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

    // Decode the image file into a Bitmap sized to fill the View
    bmOptions.inJustDecodeBounds = false
    bmOptions.inSampleSize = scaleFactor

    return BitmapFactory.decodeFile(imagePath)
}