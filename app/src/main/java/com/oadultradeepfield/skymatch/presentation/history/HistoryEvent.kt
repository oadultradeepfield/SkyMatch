package com.oadultradeepfield.skymatch.presentation.history

import com.oadultradeepfield.skymatch.presentation.base.UiEvent

/** Sealed interface for representing history events. */
sealed interface HistoryEvent : UiEvent {
  /**
   * Event to show an error message.
   *
   * @param message The error message to be displayed.
   */
  data class ShowError(val message: String) : HistoryEvent
}
