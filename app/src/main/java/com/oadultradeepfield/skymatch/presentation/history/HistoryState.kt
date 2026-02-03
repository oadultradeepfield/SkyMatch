package com.oadultradeepfield.skymatch.presentation.history

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.presentation.base.UiState

/**
 * Data class representing the state of the history section.
 *
 * @param histories The list of solving histories.
 * @param isLoading Indicates whether the histories are currently loading.
 * @param error The error message, if any, encountered during loading.
 */
data class HistoryState(
    val histories: List<SolvingHistory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
) : UiState
