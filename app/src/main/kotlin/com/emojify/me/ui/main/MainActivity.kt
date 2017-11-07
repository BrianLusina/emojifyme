package com.emojify.me.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.emojify.me.R
import com.emojify.me.ui.base.BaseActivity
import com.emojify.me.utils.BitmapUtils
import com.emojify.me.utils.Emojifier
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, View.OnClickListener {

    private var mTempPhotoPath: String? = null

    private var mResultsBitmap: Bitmap? = null

    private val requestImageCapture = 1
    private val requestStoragePermission = 1

    @Inject
    lateinit var mainPresenter: MainPresenter<MainView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent.injectMain(this)

        mainPresenter.onAttach(this)
    }

    override fun setupViewListeners() {
        emojify_button.setOnClickListener(this)
        clear_button.setOnClickListener(this)
        save_button.setOnClickListener(this)
        share_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            emojify_button -> {
                mainPresenter.onEmojifyMeBtnClicked()
            }

            clear_button -> {
                mainPresenter.onClearBtnClicked()
            }

            save_button -> {
                mainPresenter.onSaveBtnClicked()
            }

            share_button -> {
                mainPresenter.onShareBtnClicked()
            }
        }
    }

    /**
     * Launches the camera app.
     */
    override fun emojifyMe() {
        // Check for the external storage permission
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // If you do not have permission, request it
            requestPermissionsSafely(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestStoragePermission)
        } else {
            // Launch the camera if the permission exists
            launchCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        // Called when you request permission to read and write to external storage
        when (requestCode) {
            requestStoragePermission -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainPresenter.onPermissionsGranted()
                } else {
                    mainPresenter.onPermissionDenied()
                }
            }
        }
    }

    override fun displayPermissionRationale() {
        // If you do not get permission, show a Toast
        Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show()
    }

    /**
     * Creates a temporary image file and captures a picture to store in it.
     */
    override fun launchCamera() {
        // Create the capture image intent
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoUri = mainPresenter.onTakePicture()
            // Add the URI so the camera can store the image
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            // Launch the camera activity
            startActivityForResult(takePictureIntent, requestImageCapture)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // If the image capture activity was called and was successful
        if (requestCode == requestImageCapture && resultCode == Activity.RESULT_OK) {
            // Process the image and set it to the TextView
            mainPresenter.onActivityResultSuccess()
        } else {
            // Otherwise, delete the temporary image file
            mainPresenter.onActivityResultFailed()
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    override fun processAndSetImage() {
        // Toggle Visibility of the views
        emojify_button.visibility = View.GONE
        title_text_view.visibility = View.GONE
        save_button.visibility = View.VISIBLE
        share_button.visibility = View.VISIBLE
        clear_button.visibility = View.VISIBLE

        mainPresenter.onResamplePicRequest()
    }

    override fun resamplePic(photoPath: String) {
        // Resample the saved image to fit the ImageView
        mResultsBitmap = BitmapUtils.resamplePic(this, photoPath)

        // Detect the faces and overlay the appropriate emoji
        mResultsBitmap = Emojifier.detectFacesandOverlayEmoji(this, mResultsBitmap)

        // Set the new bitmap to the ImageView
        image_view.setImageBitmap(mResultsBitmap)
    }

    /**
     * OnClick method for the save button.
     */
    fun saveMe() {
        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath)

        // Save the image
        BitmapUtils.saveImage(this, mResultsBitmap)
    }

    /**
     * OnClick method for the share button, saves and shares the new bitmap.
     */
    fun shareMe() {
        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath)

        // Save the image
        BitmapUtils.saveImage(this, mResultsBitmap)

        // Share the image
        BitmapUtils.shareImage(this, mTempPhotoPath)
    }

    /**
     * OnClick for the clear button, resets the app to original state.
     */
    override fun clearImage() {
        // Clear the image and toggle the view visibility
        image_view.setImageResource(0)
        emojify_button.visibility = View.VISIBLE
        title_text_view.visibility = View.VISIBLE
        share_button.visibility = View.GONE
        save_button.visibility = View.GONE
        clear_button.visibility = View.GONE
    }
}
