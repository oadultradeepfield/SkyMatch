package com.oadultradeepfield.skymatch.domain.repository

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import kotlinx.coroutines.flow.Flow

/** Repository interface for managing solving histories. */
interface IHistoryRepository {
  /**
   * Observes all solving histories.
   *
   * @return A flow of lists of solving histories, ordered by creation time (newest first).
   */
  fun observeHistories(): Flow<List<SolvingHistory>>

  /**
   * Creates a new empty solving history.
   *
   * @return The ID of the created solving history.
   */
  suspend fun createHistory(): String

  /**
   * Adds solving results to an existing history.
   *
   * @param historyId The ID of the history to add results to.
   * @param resultIds The IDs of solving results to add.
   */
  suspend fun addResults(historyId: String, resultIds: List<String>)

  /**
   * Removes solving results from an existing history.
   *
   * @param historyId The ID of the history to remove results from.
   * @param resultIds The IDs of solving results to remove.
   */
  suspend fun removeResults(historyId: String, resultIds: List<String>)

  /**
   * Deletes a specific solving history.
   *
   * @param historyId The ID of the solving history to delete.
   */
  suspend fun deleteHistory(historyId: String)

  /**
   * Gets a specific solving history by ID.
   *
   * @param historyId The ID of the solving history to retrieve.
   * @return The solving history, or null if not found.
   */
  suspend fun getHistory(historyId: String): SolvingHistory?
}
