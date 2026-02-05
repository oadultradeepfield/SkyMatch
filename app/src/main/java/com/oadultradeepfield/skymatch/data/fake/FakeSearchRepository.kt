package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.config.AppConfig
import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.domain.repository.ISearchRepository
import javax.inject.Inject
import kotlinx.coroutines.delay

/** Fake implementation of [ISearchRepository] for testing. */
class FakeSearchRepository @Inject constructor() : ISearchRepository {
    private val networkDelayMs = AppConfig.Network.DEFAULT_DELAY_MS

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
