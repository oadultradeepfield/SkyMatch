package com.oadultradeepfield.skymatch.presentation.history

import com.oadultradeepfield.skymatch.presentation.base.UiIntent

/** Sealed interface for representing history intents. */
sealed interface HistoryIntent : UiIntent {
  /** Intent to load all histories. */
  data object LoadHistories : HistoryIntent

  /**
   * Intent to delete a specific history.
   *
   * @param historyId The ID of the history to delete.
   */
  data class DeleteHistory(val historyId: String) : HistoryIntent
}
