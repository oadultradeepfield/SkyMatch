package com.oadultradeepfield.skymatch.domain.usecase.history

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import javax.inject.Inject

/**
 * Use case for creating a new solving history with fixed solving results.
 *
 * @param repo The repository for creating solving histories.
 */
class CreateHistoryUseCase @Inject constructor(private val repo: IHistoryRepository) {
  /**
   * Creates a new solving history with fixed solving results.
   *
   * @param initialResults The initial list of solving results for the history.
   * @return The ID of the created solving history.
   */
  suspend fun invoke(initialResults: List<SolvingResult>): String =
      repo.createHistory(initialResults)
}
