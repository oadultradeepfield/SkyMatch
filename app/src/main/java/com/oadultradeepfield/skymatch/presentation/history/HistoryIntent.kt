package com.oadultradeepfield.skymatch.presentation.history

import com.oadultradeepfield.skymatch.presentation.base.UiIntent

/** Sealed interface for representing history intents. */
sealed interface HistoryIntent : UiIntent {
    /** Intent to refresh histories by cancelling and restarting observation. */
    data object RefreshHistories : HistoryIntent

    /** Intent to delete a specific history. */
    data class DeleteHistory(val historyId: String) : HistoryIntent
}
