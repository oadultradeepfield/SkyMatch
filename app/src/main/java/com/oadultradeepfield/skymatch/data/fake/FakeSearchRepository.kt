package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.domain.repository.ISearchRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

/** A fake implementation of [ISearchRepository] for testing purposes. */
class FakeSearchRepository @Inject constructor() : ISearchRepository {
  private val networkDelayMs: Long = 500L

  private val mockConstellations =
      mapOf(
          "orion" to
              Constellation(
                  latinName = "Orion",
                  englishName = "The Hunter",
                  imageUrl = "https://www.davidmalin.com/fujii/image/Ori_www.jpg",
              ),
          "ursa major" to
              Constellation(
                  latinName = "Ursa Major",
                  englishName = "The Great Bear",
                  imageUrl = "https://www.davidmalin.com/fujii/image/UMa_www.jpg",
              ),
          "cassiopeia" to
              Constellation(
                  latinName = "Cassiopeia",
                  englishName = "The Seated Queen",
                  imageUrl = "https://www.davidmalin.com/fujii/image/Cas_www.jpg",
              ),
          "andromeda" to
              Constellation(
                  latinName = "Crux",
                  englishName = "The Southern Cross",
                  imageUrl = "https://www.davidmalin.com/fujii/image/Cru_www.jpg",
              ),
      )

  override suspend fun searchConstellation(name: String): Constellation? {
    delay(networkDelayMs)

    val normalizedName = name.trim().lowercase()
    return mockConstellations[normalizedName]
  }
}
