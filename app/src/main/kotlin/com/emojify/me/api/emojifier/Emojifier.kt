package com.emojify.me.api.emojifier


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.widget.Toast

import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector

import com.emojify.me.R

object Emojifier {

    /**
     * Method for detecting faces in a bitmap, and drawing emoji depending on the facial
     * expression.
     *
     * @param context The application context.
     * @param picture The picture in which to detect the faces.
     */
    fun detectFacesAndOverlayEmoji(context: Context, picture: Bitmap): Bitmap {

        // Create the face detector, disable tracking and enable classifications
        val detector = FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build()

        // Build the frame
        val frame = Frame.Builder().setBitmap(picture).build()

        // Detect the faces
        val faces = detector.detect(frame)

        // Initialize result bitmap to original picture
        var resultBitmap = picture

        // If there are no faces detected, show a Toast message
        if (faces.size() == 0) {
            Toast.makeText(context, R.string.no_faces_message, Toast.LENGTH_SHORT).show()
        } else {
            // Iterate through the faces
            for (i in 0 until faces.size()) {
                val face = faces.valueAt(i)

                val emojiBitmap: Bitmap?
                when (whichEmoji(face)) {
                    Emoji.SMILE -> emojiBitmap = BitmapFactory.decodeResource(context.resources,
                            R.drawable.smile)
                    Emoji.FROWN -> emojiBitmap = BitmapFactory.decodeResource(context.resources,
                            R.drawable.frown)
                    Emoji.LEFT_WINK -> emojiBitmap = BitmapFactory.decodeResource(context.resources,
                            R.drawable.leftwink)
                    Emoji.RIGHT_WINK -> emojiBitmap = BitmapFactory.decodeResource(context.resources,
                            R.drawable.rightwink)
                    Emoji.LEFT_WINK_FROWN -> emojiBitmap = BitmapFactory.decodeResource(context.resources,
                            R.drawable.leftwinkfrown)
                    Emoji.RIGHT_WINK_FROWN -> emojiBitmap = BitmapFactory.decodeResource(context.resources,
                            R.drawable.rightwinkfrown)
                    Emoji.CLOSED_EYE_SMILE -> emojiBitmap = BitmapFactory.decodeResource(context.resources,
                            R.drawable.closed_smile)
                    Emoji.CLOSED_EYE_FROWN -> emojiBitmap = BitmapFactory.decodeResource(context.resources,
                            R.drawable.closed_frown)
                    else -> {
                        emojiBitmap = null
                        Toast.makeText(context, R.string.no_emoji, Toast.LENGTH_SHORT).show()
                    }
                }

                // Add the emojiBitmap to the proper position in the original image
                resultBitmap = addBitmapToFace(resultBitmap, emojiBitmap, face)
            }
        }


        // Release the detector
        detector.release()

        return resultBitmap
    }


    /**
     * Determines the closest emoji to the expression on the face, based on the
     * odds that the person is smiling and has each eye open.
     *
     * @param face The face for which you pick an emoji.
     */

    private fun whichEmoji(face: Face): Emoji {

        val smiling = face.isSmilingProbability > SMILING_PROB_THRESHOLD

        val leftEyeClosed = face.isLeftEyeOpenProbability < EYE_OPEN_PROB_THRESHOLD
        val rightEyeClosed = face.isRightEyeOpenProbability < EYE_OPEN_PROB_THRESHOLD


        // Determine and log the appropriate emoji
        val emoji: Emoji
        if (smiling) {
            if (leftEyeClosed && !rightEyeClosed) {
                emoji = Emoji.LEFT_WINK
            } else if (rightEyeClosed && !leftEyeClosed) {
                emoji = Emoji.RIGHT_WINK
            } else if (leftEyeClosed) {
                emoji = Emoji.CLOSED_EYE_SMILE
            } else {
                emoji = Emoji.SMILE
            }
        } else {
            if (leftEyeClosed && !rightEyeClosed) {
                emoji = Emoji.LEFT_WINK_FROWN
            } else if (rightEyeClosed && !leftEyeClosed) {
                emoji = Emoji.RIGHT_WINK_FROWN
            } else if (leftEyeClosed) {
                emoji = Emoji.CLOSED_EYE_FROWN
            } else {
                emoji = Emoji.FROWN
            }
        }

        // return the chosen Emoji
        return emoji
    }

    /**
     * Combines the original picture with the emoji bitmaps
     *
     * @param backgroundBitmap The original picture
     * @param emojiBitmap      The chosen emoji
     * @param face             The detected face
     * @return The final bitmap, including the emojis over the faces
     */
    private fun addBitmapToFace(backgroundBitmap: Bitmap, emojiBitmap: Bitmap?, face: Face): Bitmap {
        var emojiBitmap_ = emojiBitmap

        // Initialize the results bitmap to be a mutable copy of the original image
        val resultBitmap = Bitmap.createBitmap(backgroundBitmap.width,
                backgroundBitmap.height, backgroundBitmap.config)

        // Scale the emoji so it looks better on the face
        val scaleFactor = EMOJI_SCALE_FACTOR

        // Determine the size of the emoji to match the width of the face and preserve aspect ratio
        val newEmojiWidth = (face.width * scaleFactor).toInt()
        val newEmojiHeight = (emojiBitmap_!!.height * newEmojiWidth / emojiBitmap_.width * scaleFactor).toInt()


        // Scale the emoji
        emojiBitmap_ = Bitmap.createScaledBitmap(emojiBitmap_, newEmojiWidth, newEmojiHeight, false)

        // Determine the emoji position so it best lines up with the face
        val emojiPositionX = face.position.x + face.width / 2 - emojiBitmap_!!.width / 2
        val emojiPositionY = face.position.y + face.height / 2 - emojiBitmap_.height / 3

        // Create the canvas and draw the bitmaps to it
        val canvas = Canvas(resultBitmap)
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null)
        canvas.drawBitmap(emojiBitmap_, emojiPositionX, emojiPositionY, null)

        return resultBitmap
    }

}
