package com.oadultradeepfield.skymatch.domain.repository

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing solved results history.
 */
interface IHistoryRepository {
    /**
     * Observes all solving histories.
     *
     * @return A flow of lists of solving histories.
     */
    fun observeHistories(): Flow<List<SolvingHistory>>

    /**
     * Creates a new solving history with fixed solving results.
     *
     * @param initialResults The initial list of solving results for the history.
     * @return The ID of the created solving history.
     */
    suspend fun createHistory(
        initialResults: List<SolvingResult>
    ): String

    /**
     * Updates a specific solving result in a solving history.
     *
     * @param historyId The ID of the solving history to update.
     * @param result The solving result to update.
     */
    suspend fun updateSolvingResult(
        historyId: String,
        result: SolvingResult
    )

    /**
     * Updates multiple solving results in a solving history.
     *
     * @param historyId The ID of the solving history to update.
     * @param results The list of solving results to update.
     */
    suspend fun updateSolvingResults(
        historyId: String,
        results: List<SolvingResult>
    )

    /**
     * Deletes a specific solving history by its ID.
     *
     * @param historyId The ID of the solving history to delete.
     */
    suspend fun deleteHistory(historyId: String)

    /**
     * Clears all solving histories.
     */
    suspend fun clearAll()
}