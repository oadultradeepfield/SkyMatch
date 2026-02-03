package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fake implementation of the [IHistoryRepository] interface.
 *
 * This repository stores history entries in memory and provides methods to add, retrieve, and clear
 * history entries.
 *
 * @param solveRepository The repository used to access solve data.
 */
@Singleton
class FakeHistoryRepository @Inject constructor(private val solveRepository: ISolveRepository) :
    IHistoryRepository {

  private val histories = MutableStateFlow<List<MockData.HistoryData>>(MockData.initialHistories)

  override fun observeHistories(): Flow<List<SolvingHistory>> {
    val solveRepo = solveRepository as? FakeSolveRepository

    return histories.map { historyDataList ->
      historyDataList
          .map { data ->
            val results =
                data.resultIds.mapNotNull { id ->
                  // We know that the data is from the solve repository (manual user testing) if
                  // its id is not in the mock data mapping.
                  MockData.solvingResults[id] ?: solveRepo?.getResult(id)
                }
            SolvingHistory(id = data.id, solvingResults = results, createdAt = data.createdAt)
          }
          .sortedByDescending { it.createdAt }
    }
  }

  override suspend fun createHistory(): String {
    val historyId = UUID.randomUUID().toString()
    val history =
        MockData.HistoryData(
            id = historyId,
            resultIds = mutableListOf(),
            createdAt = Instant.now(),
        )

    histories.update { current -> current + history }
    return historyId
  }

  override suspend fun addResults(historyId: String, resultIds: List<String>) {
    histories.update { current ->
      current.map { history ->
        if (history.id != historyId) return@map history

        // Make a copy of the history and overwrite the result IDs with the concatenation of the new
        // and the original
        history.copy(resultIds = (history.resultIds + resultIds).distinct().toMutableList())
      }
    }
  }

  override suspend fun removeResults(historyId: String, resultIds: List<String>) {
    histories.update { current ->
      current.map { history ->
        if (history.id != historyId) return@map history

        // Make a copy of the history and overwrite the result IDs with the difference of the new
        // (converted to set for performance) and the original
        history.copy(resultIds = (history.resultIds - resultIds.toSet()).distinct().toMutableList())
      }
    }
  }

  override suspend fun deleteHistory(historyId: String) {
    histories.update { current -> current.filter { it.id != historyId } }
  }

  override suspend fun clearAll() {
    histories.update { emptyList() }
  }
}
