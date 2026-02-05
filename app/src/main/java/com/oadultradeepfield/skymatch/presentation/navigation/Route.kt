package com.oadultradeepfield.skymatch.presentation.navigation

import kotlinx.serialization.Serializable

/** Type-safe navigation routes for the app. */
@Serializable
sealed class Route {
  /** Home screen - displays history and upload button. */
  @Serializable data object Home : Route()

  /** Search screen - constellation search with filtering. */
  @Serializable data object Search : Route()

  /** Solving screen - processes uploaded images or resumes existing history. */
  @Serializable
  data class Solving(
      val imageUris: List<String> = emptyList(),
      val historyId: String? = null,
  ) : Route()
}
