package com.oadultradeepfield.skymatch.presentation.solving

import com.oadultradeepfield.skymatch.presentation.base.UiEvent

/** Sealed interface for representing solving screen events. */
sealed interface SolvingEvent : UiEvent {
  /** Event to navigate back to the previous screen. */
  data object NavigateBack : SolvingEvent

  /** Event to navigate to history screen (used when all results are deleted). */
  data object NavigateToHistory : SolvingEvent

  /**
   * Event to show an error message.
   *
   * @param message The error message to be displayed.
   */
  data class ShowError(val message: String) : SolvingEvent
}
