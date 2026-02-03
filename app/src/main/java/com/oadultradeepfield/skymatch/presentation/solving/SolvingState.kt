package com.oadultradeepfield.skymatch.presentation.solving

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.presentation.base.UiState

/**
 * Data class representing the state of the solving screen.
 *
 * @param historyId The ID of the history entry for this solving session.
 * @param items The list of solving results being processed.
 * @param currentIndex The current page index in the pager.
 * @param isLoading Indicates whether the screen is in loading state.
 * @param error The error message, if any.
 */
data class SolvingState(
    val historyId: String? = null,
    val items: List<SolvingResult> = emptyList(),
    val currentIndex: Int = 0,
    val isLoading: Boolean = true,
    val error: String? = null,
) : UiState
