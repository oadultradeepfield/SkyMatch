package com.oadultradeepfield.skymatch.presentation.search

import com.oadultradeepfield.skymatch.presentation.base.UiEvent

/** Sealed interface for representing search events. */
sealed interface SearchEvent : UiEvent {
  /**
   * Event to show an error message.
   *
   * @param message The error message to be displayed.
   */
  data class ShowError(val message: String) : SearchEvent
}
