package com.oadultradeepfield.skymatch.domain.usecase.history

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import javax.inject.Inject

/**
 * Use case for fetching a single solving history by ID.
 *
 * @param repo The repository for fetching solving histories.
 */
class GetHistoryUseCase @Inject constructor(private val repo: IHistoryRepository) {
  /**
   * Fetches a solving history by its ID.
   *
   * @param historyId The ID of the history to fetch.
   * @return The solving history, or null if not found.
   */
  suspend operator fun invoke(historyId: String): SolvingHistory? = repo.getHistory(historyId)
}
