package com.oadultradeepfield.skymatch.domain.usecase.history

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for observing all solving histories.
 *
 * @param repo The repository for observing solving histories.
 */
class ObserveHistoryUseCase @Inject constructor(private val repo: IHistoryRepository) {
  /**
   * Observes all solving histories.
   *
   * @return A flow of lists of solving histories.
   */
  operator fun invoke(): Flow<List<SolvingHistory>> = repo.observeHistories()
}
