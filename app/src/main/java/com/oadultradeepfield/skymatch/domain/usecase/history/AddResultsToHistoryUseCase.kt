package com.oadultradeepfield.skymatch.domain.usecase.history

import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import javax.inject.Inject

/**
 * Use case for adding solving results to an existing history.
 *
 * @param repo The repository for adding solving results to histories.
 */
class AddResultsToHistoryUseCase @Inject constructor(private val repo: IHistoryRepository) {
  /**
   * Adds solving results to an existing history.
   *
   * @param historyId The ID of the history to add results to.
   * @param resultIds The IDs of solving results to add.
   */
  suspend operator fun invoke(historyId: String, resultIds: List<String>) {
    repo.addResults(historyId, resultIds)
  }
}
