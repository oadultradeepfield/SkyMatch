package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.domain.repository.ISearchRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

/** A fake implementation of [ISearchRepository] for testing purposes. */
class FakeSearchRepository @Inject constructor() : ISearchRepository {
  private val networkDelayMs: Long = 500L

  private val mockConstellations =
      listOf(
          Constellation(
              latinName = "Orion",
              englishName = "The Hunter",
              imageUrl = "https://www.davidmalin.com/fujii/image/Ori_www.jpg",
          ),
          Constellation(
              latinName = "Ursa Major",
              englishName = "The Great Bear",
              imageUrl = "https://www.davidmalin.com/fujii/image/UMa_www.jpg",
          ),
          Constellation(
              latinName = "Ursa Minor",
              englishName = "The Little Bear",
              imageUrl = "https://www.davidmalin.com/fujii/image/UMi_www.jpg",
          ),
          Constellation(
              latinName = "Cassiopeia",
              englishName = "The Seated Queen",
              imageUrl = "https://www.davidmalin.com/fujii/image/Cas_www.jpg",
          ),
          Constellation(
              latinName = "Leo",
              englishName = "The Lion",
              imageUrl = "https://www.davidmalin.com/fujii/image/Leo_www.jpg",
          ),
          Constellation(
              latinName = "Crux",
              englishName = "The Southern Cross",
              imageUrl = "https://www.davidmalin.com/fujii/image/Cru_www.jpg",
          ),
          Constellation(
              latinName = "Aquarius",
              englishName = "The Water Bearer",
              imageUrl = "https://www.davidmalin.com/fujii/image/Aqr_www.jpg",
          ),
          Constellation(
              latinName = "Draco",
              englishName = "The Dragon",
              imageUrl = "https://www.davidmalin.com/fujii/image/Dra_www.jpg",
          ),
      )

  override suspend fun getAllConstellations(): List<Constellation> {
    delay(networkDelayMs)
    return mockConstellations
  }

  override suspend fun searchConstellations(query: String): List<Constellation> {
    delay(networkDelayMs)

    if (query.isBlank()) {
      return mockConstellations
    }

    val normalizedQuery = query.trim().lowercase()
    return mockConstellations.filter { constellation ->
      constellation.latinName.lowercase().contains(normalizedQuery) ||
          constellation.englishName.lowercase().contains(normalizedQuery)
    }
  }
}
