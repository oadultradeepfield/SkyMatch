package com.oadultradeepfield.skymatch.presentation.base

/**
 * Sealed interface for representing UI states.
 */
sealed interface UiState {
    /**
     * Represents the idle state of the UI.
     */
    data object Idle : UiState

    /**
     * Represents the loading state of the UI.
     */
    data object Loading : UiState

    /**
     * Represents the content state of the UI with associated data.
     *
     * @param T The type of data to be displayed.
     */
    data class Content<T>(val data: T) : UiState

    /**
     * Represents the error state of the UI with an error message and optional throwable.
     *
     * @param message The error message to be displayed.
     * @param throwable The optional throwable associated with the error (default is null).
     */
    data class Error(val message: String, val throwable: Throwable? = null) : UiState
}