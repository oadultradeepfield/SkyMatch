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
  /**
   * Represents a history entry in the fake repository.
   *
   * @param id The unique identifier of the history entry.
   * @param resultIds The list of result IDs associated with the history entry.
   * @param createdAt The timestamp when the history entry was created.
   */
  private data class HistoryData(
      val id: String,
      val resultIds: List<String>,
      val createdAt: Instant,
  )

  private val histories = MutableStateFlow<List<HistoryData>>(emptyList())

  override fun observeHistories(): Flow<List<SolvingHistory>> {
    return histories.map { historyDataList ->
      historyDataList
          .map { data ->
            val solveRepo = solveRepository as? FakeSolveRepository
            val results = data.resultIds.mapNotNull { id -> solveRepo?.getResult(id) }

            SolvingHistory(id = data.id, solvingResults = results, createdAt = data.createdAt)
          }
          .sortedByDescending { it.createdAt }
    }
  }

  override suspend fun createHistory(): String {
    val historyId = UUID.randomUUID().toString()
    val history =
        HistoryData(
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
