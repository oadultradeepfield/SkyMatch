package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.config.AppConfig
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/** Fake implementation of [IHistoryRepository] for testing. */
@Singleton
class FakeHistoryRepository
@Inject
constructor(
    private val solveRepository: ISolveRepository,
) : IHistoryRepository {
  private val histories = MutableStateFlow<List<MockData.HistoryData>>(MockData.initialHistories)
  private val networkDelayMs = AppConfig.Network.DEFAULT_DELAY_MS

  override fun observeHistories(): Flow<List<SolvingHistory>> {
    return histories.map { historyDataList ->
      historyDataList
          .map { data ->
            val results =
                data.resultIds.mapNotNull { id ->
                  MockData.solvingResults[id] ?: solveRepository.getResult(id)
                }
            SolvingHistory(id = data.id, solvingResults = results, createdAt = data.createdAt)
          }
          .sortedByDescending { it.createdAt }
    }
  }

  override suspend fun createHistory(): String {
    delay(networkDelayMs)
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
    delay(networkDelayMs)
    histories.update { current ->
      current.map { history ->
        if (history.id != historyId) return@map history
        history.copy(resultIds = (history.resultIds + resultIds).distinct().toMutableList())
      }
    }
  }

  override suspend fun removeResults(historyId: String, resultIds: List<String>) {
    delay(networkDelayMs)
    histories.update { current ->
      current.map { history ->
        if (history.id != historyId) return@map history
        history.copy(resultIds = (history.resultIds - resultIds.toSet()).distinct().toMutableList())
      }
    }
  }

  override suspend fun deleteHistory(historyId: String) {
    delay(networkDelayMs)
    histories.update { current -> current.filter { it.id != historyId } }
  }
}
