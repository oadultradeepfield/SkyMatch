package com.oadultradeepfield.skymatch.domain.usecase.history

import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import javax.inject.Inject

/**
 * Use case for deleting solving histories.
 *
 * @param repo The repository for deleting solving histories.
 */
class DeleteHistoryUseCase @Inject constructor(private val repo: IHistoryRepository) {
  /**
   * Deletes a specific solving history by its ID.
   *
   * @param historyId The ID of the solving history to delete.
   */
  suspend operator fun invoke(historyId: String) = repo.deleteHistory(historyId)

  /** Clears all solving histories. */
  suspend fun clearAll() = repo.clearAll()
}
