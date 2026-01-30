package com.oadultradeepfield.skymatch.domain.usecase.history

import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import javax.inject.Inject

/**
 * Use case for removing solving results from an existing history.
 *
 * @param repo The repository for removing solving results from histories.
 */
class RemoveResultsFromHistoryUseCase @Inject constructor(private val repo: IHistoryRepository) {
  /**
   * Removes solving results from an existing history.
   *
   * @param historyId The ID of the history to remove results from.
   * @param resultIds The IDs of solving results to remove.
   */
  suspend operator fun invoke(historyId: String, resultIds: List<String>) {
    repo.removeResults(historyId, resultIds)
  }
}
