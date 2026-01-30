package com.oadultradeepfield.skymatch.presentation.upload

import android.net.Uri
import com.oadultradeepfield.skymatch.presentation.base.UiIntent

/** Sealed interface for representing upload intents. */
sealed interface UploadIntent : UiIntent {
  /**
   * Intent to select images for upload.
   *
   * @param uris The list of URIs of the selected images.
   */
  data class ImagesSelected(val uris: List<Uri>) : UploadIntent

  /**
   * Intent to remove an image from the upload list.
   *
   * @param uri The URI of the image to be removed.
   */
  data class ImageRemoved(val uri: Uri) : UploadIntent

  /** Intent to clear all selected images from the upload list. */
  data object ClearAllImages : UploadIntent

  /** Intent to start solving the uploaded images. */
  data object StartSolving : UploadIntent
}
