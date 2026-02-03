package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.domain.repository.ISearchRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

/** A fake implementation of [ISearchRepository] for testing purposes. */
class FakeSearchRepository @Inject constructor() : ISearchRepository {
  private val networkDelayMs: Long = 500L

  override suspend fun searchConstellations(query: String): List<Constellation> {
    delay(networkDelayMs)

    if (query.isBlank()) {
      return MockData.constellations
    }

    val normalizedQuery = query.trim().lowercase()
    return MockData.constellations.filter { constellation ->
      constellation.latinName.lowercase().contains(normalizedQuery) ||
          constellation.englishName.lowercase().contains(normalizedQuery)
    }
  }
}
