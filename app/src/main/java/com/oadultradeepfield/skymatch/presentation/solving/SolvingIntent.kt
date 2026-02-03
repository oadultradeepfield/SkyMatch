package com.oadultradeepfield.skymatch.presentation.solving

import com.oadultradeepfield.skymatch.presentation.base.UiIntent

/** Sealed interface for representing solving screen intents. */
sealed interface SolvingIntent : UiIntent {
  /**
   * Intent to initialize the solving process with selected images.
   *
   * @param imageUris The list of URIs of the selected images.
   */
  data class Initialize(val imageUris: List<String>) : SolvingIntent

  /**
   * Intent to cancel solving for a specific image.
   *
   * @param index The index of the image to cancel.
   */
  data class CancelSolving(val index: Int) : SolvingIntent

  /** Intent to cancel all ongoing solving processes. */
  data object CancelAll : SolvingIntent

  /**
   * Intent to delete a completed/cancelled result.
   *
   * @param index The index of the result to delete.
   */
  data class DeleteResult(val index: Int) : SolvingIntent
}
