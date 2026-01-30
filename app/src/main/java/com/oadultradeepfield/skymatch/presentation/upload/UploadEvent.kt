package com.oadultradeepfield.skymatch.presentation.upload

import com.oadultradeepfield.skymatch.presentation.base.UiEvent

/** Sealed interface for representing upload events. */
sealed interface UploadEvent : UiEvent {
  /**
   * Event to navigate to the solving screen with the selected image URIs.
   *
   * @param imageUris The list of URIs of the selected images for solving.
   */
  data class NavigateToSolving(val imageUris: List<String>) : UploadEvent

  /**
   * Event to show an error message.
   *
   * @param message The error message to be displayed.
   */
  data class ShowError(val message: String) : UploadEvent
}
