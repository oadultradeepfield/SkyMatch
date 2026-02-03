package com.oadultradeepfield.skymatch.presentation.upload

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.oadultradeepfield.skymatch.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** ViewModel for handling upload intents and events. */
@HiltViewModel
class UploadViewModel @Inject constructor() :
    BaseViewModel<UploadIntent, UploadState, UploadEvent>(UploadState()) {
  override suspend fun handleIntent(intent: UploadIntent) {
    when (intent) {
      is UploadIntent.ImagesSelected -> handleImagesSelected(intent.uris)
      is UploadIntent.ImageRemoved -> handleImageRemoved(intent.uri)
      is UploadIntent.ClearAllImages -> handleClearAllImages(intent)
      is UploadIntent.StartSolving -> handleStartSolving(intent)
    }
  }

  override fun handleError(error: Exception) {
    val errorMessage = error.message ?: "Unknown error occurred. Please try again."
    setState { copy(error = errorMessage, isLoading = false) }
    viewModelScope.launch { sendEvent(UploadEvent.ShowError(errorMessage)) }
  }

  /**
   * Handles the intent to select images for upload.
   *
   * @param uris The list of URIs of the selected images.
   */
  private fun handleImagesSelected(uris: List<Uri>) {
    setState {
      val newImages = (selectedImages + uris).distinct()
      copy(selectedImages = newImages, canStartSolving = newImages.isNotEmpty(), error = null)
    }
  }

  /**
   * Handles the intent to remove an image from the upload list.
   *
   * @param uri The URI of the image to be removed.
   */
  private fun handleImageRemoved(uri: Uri) {
    setState {
      val newImages = selectedImages.filterNot { it == uri }
      copy(selectedImages = newImages, canStartSolving = newImages.isNotEmpty(), error = null)
    }
  }

  /**
   * Handles the intent to clear all selected images from the upload list.
   *
   * @param intent The intent to clear all images.
   */
  private fun handleClearAllImages(intent: UploadIntent.ClearAllImages) {
    setState { copy(selectedImages = emptyList(), canStartSolving = false, error = null) }
  }

  /**
   * Handles the intent to start solving the uploaded images.
   *
   * @param intent The intent to start solving.
   */
  private fun handleStartSolving(intent: UploadIntent.StartSolving) {
    val currentState = state.value
    if (currentState.selectedImages.isEmpty()) {
      handleError(Exception("Please select at least one image"))
      return
    }

    setState { copy(isLoading = true) }

    viewModelScope.launch {
      sendEvent(UploadEvent.NavigateToSolving(currentState.selectedImages.map { it.toString() }))
      setState { copy(isLoading = false) }
    }
  }
}
