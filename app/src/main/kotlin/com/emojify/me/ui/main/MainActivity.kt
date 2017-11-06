package com.emojify.me.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.widget.Toast
import com.emojify.me.R
import com.emojify.me.ui.base.BaseActivity
import com.emojify.me.utils.BitmapUtils
import com.emojify.me.utils.Emojifier
import java.io.File
import java.io.IOException
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    private var mTempPhotoPath: String? = null

    private var mResultsBitmap: Bitmap? = null

    @Inject
    lateinit var mainPresenter: MainPresenter<MainView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent.injectMain(this)

        mainPresenter.onAttach(this)
    }

    /**
     * OnClick method for "Emojify Me!" Button. Launches the camera app.
     */
    fun emojifyMe() {
        // Check for the external storage permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_STORAGE_PERMISSION)
        } else {
            // Launch the camera if the permission exists
            launchCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        // Called when you request permission to read and write to external storage
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchCamera()
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Creates a temporary image file and captures a picture to store in it.
     */
    private fun launchCamera() {

        // Create the capture image intent
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the temporary File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = BitmapUtils.createTempImageFile(this)
            } catch (ex: IOException) {
                // Error occurred while creating the File
                ex.printStackTrace()
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.absolutePath

                // Get the content URI for the image file
                val photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile)

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage()
        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath)
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private fun processAndSetImage() {

        // Toggle Visibility of the views
        //        mEmojifyButton.setVisibility(View.GONE);
        //        mTitleTextView.setVisibility(View.GONE);
        //        mSaveFab.setVisibility(View.VISIBLE);
        //        mShareFab.setVisibility(View.VISIBLE);
        //        mClearFab.setVisibility(View.VISIBLE);

        // Resample the saved image to fit the ImageView
        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath)


        // Detect the faces and overlay the appropriate emoji
        mResultsBitmap = Emojifier.detectFacesandOverlayEmoji(this, mResultsBitmap)

        //        // Set the new bitmap to the ImageView
        //        mImageView.setImageBitmap(mResultsBitmap);
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
    fun clearImage() {
        // Clear the image and toggle the view visibility
        //        mImageView.setImageResource(0);
        //        mEmojifyButton.setVisibility(View.VISIBLE);
        //        mTitleTextView.setVisibility(View.VISIBLE);
        //        mShareFab.setVisibility(View.GONE);
        //        mSaveFab.setVisibility(View.GONE);
        //        mClearFab.setVisibility(View.GONE);

        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath)
    }

    companion object {


        private val REQUEST_IMAGE_CAPTURE = 1
        private val REQUEST_STORAGE_PERMISSION = 1
    }
}
