package com.oadultradeepfield.skymatch.domain.usecase.history

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import javax.inject.Inject

/**
 * Use case for updating a specific solving result in a solving history.
 *
 * @param repo The repository for updating solving results.
 */
class UpdateHistoryUseCase @Inject constructor(private val repo: IHistoryRepository) {
  /**
   * Updates a specific solving result in a solving history.
   *
   * @param historyId The ID of the solving history to update.
   * @param result The solving result to update.
   */
  suspend operator fun invoke(historyId: String, result: SolvingResult) =
      repo.updateSolvingResult(historyId, result)

  /**
   * Updates multiple solving results in a solving history.
   *
   * @param historyId The ID of the solving history to update.
   * @param results The list of solving results to update.
   */
  suspend operator fun invoke(historyId: String, results: List<SolvingResult>) =
      repo.updateSolvingResults(historyId, results)
}
