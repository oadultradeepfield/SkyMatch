package com.oadultradeepfield.skymatch.presentation.search

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.presentation.base.UiState

/**
 * Data class representing the state of the search screen.
 *
 * @param query The current search query.
 * @param constellations The list of constellations matching the query.
 * @param isLoading Indicates whether the search is currently loading.
 * @param error The error message, if any, encountered during the search.
 */
data class SearchState(
    val query: String = "",
    val constellations: List<Constellation> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
) : UiState
