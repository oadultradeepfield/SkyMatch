package com.oadultradeepfield.skymatch.presentation.search

import com.oadultradeepfield.skymatch.presentation.base.UiIntent

/** Sealed interface for representing search intents. */
sealed interface SearchIntent : UiIntent {
  /**
   * Intent to update the search query.
   *
   * @param query The new search query.
   */
  data class UpdateQuery(val query: String) : SearchIntent

  /** Intent to clear the search query. */
  data object ClearQuery : SearchIntent

  /** Intent to load all constellations initially. */
  data object LoadConstellations : SearchIntent
}
