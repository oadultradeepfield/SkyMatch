package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingStatus
import java.time.Instant
import java.time.temporal.ChronoUnit

/** Centralized mock data for fake repository implementations. */
object MockData {
  /**
   * Represents a history entry in the fake repository.
   *
   * @param id The unique identifier of the history entry.
   * @param resultIds The list of result IDs associated with the history entry.
   * @param createdAt The timestamp when the history entry was created.
   */
  data class HistoryData(
      val id: String,
      val resultIds: List<String>,
      val createdAt: Instant,
  )

  val orion =
      Constellation(
          latinName = "Orion",
          englishName = "The Hunter",
          imageUrl = "https://www.davidmalin.com/fujii/image/Ori_www.jpg",
      )

  val ursaMajor =
      Constellation(
          latinName = "Ursa Major",
          englishName = "The Great Bear",
          imageUrl = "https://www.davidmalin.com/fujii/image/UMa_www.jpg",
      )

  val ursaMinor =
      Constellation(
          latinName = "Ursa Minor",
          englishName = "The Little Bear",
          imageUrl = "https://www.davidmalin.com/fujii/image/UMi_www.jpg",
      )

  val cassiopeia =
      Constellation(
          latinName = "Cassiopeia",
          englishName = "The Seated Queen",
          imageUrl = "https://www.davidmalin.com/fujii/image/Cas_www.jpg",
      )

  val leo =
      Constellation(
          latinName = "Leo",
          englishName = "The Lion",
          imageUrl = "https://www.davidmalin.com/fujii/image/Leo_www.jpg",
      )

  val crux =
      Constellation(
          latinName = "Crux",
          englishName = "The Southern Cross",
          imageUrl = "https://www.davidmalin.com/fujii/image/Cru_www.jpg",
      )

  val aquarius =
      Constellation(
          latinName = "Aquarius",
          englishName = "The Water Bearer",
          imageUrl = "https://www.davidmalin.com/fujii/image/Aqr_www.jpg",
      )

  val draco =
      Constellation(
          latinName = "Draco",
          englishName = "The Dragon",
          imageUrl = "https://www.davidmalin.com/fujii/image/Dra_www.jpg",
      )

  val constellations: List<Constellation> =
      listOf(orion, ursaMajor, ursaMinor, cassiopeia, leo, crux, aquarius, draco)

  val solvingResults: Map<String, SolvingResult> by lazy { createSolvingResults() }

  val initialHistories: List<HistoryData> by lazy {
    val today = Instant.now().truncatedTo(ChronoUnit.DAYS)
    listOf(
        HistoryData(
            id = "mock-history-1",
            resultIds = listOf("mock-result-orion", "mock-result-cassiopeia"),
            createdAt = today, // Today
        ),
        HistoryData(
            id = "mock-history-2",
            resultIds =
                listOf("mock-result-ursa-major", "mock-result-ursa-minor", "mock-result-draco"),
            createdAt = today.minus(1, ChronoUnit.DAYS), // Yesterday
        ),
        HistoryData(
            id = "mock-history-3",
            resultIds = listOf("mock-result-leo", "mock-result-aquarius"),
            createdAt = today.minus(3, ChronoUnit.DAYS), // 3 days ago
        ),
        HistoryData(
            id = "mock-history-4",
            resultIds = listOf("mock-result-crux"),
            createdAt = today.minus(7, ChronoUnit.DAYS), // 1 week ago
        ),
    )
  }

  private fun createSolvingResults(): Map<String, SolvingResult> {
    val today = Instant.now().truncatedTo(ChronoUnit.DAYS)
    return mapOf(
        // History 1 results (Today)
        "mock-result-orion" to
            SolvingResult(
                id = "mock-result-orion",
                originalImageUri = orion.imageUrl ?: "",
                solvingStatus = SolvingStatus.QUEUED,
                createdAt = today,
            ),
        "mock-result-cassiopeia" to
            SolvingResult(
                id = "mock-result-cassiopeia",
                originalImageUri = cassiopeia.imageUrl ?: "",
                solvingStatus = SolvingStatus.IDENTIFYING_OBJECTS,
                createdAt = today.plusSeconds(100),
            ),
        // History 2 results (Yesterday)
        "mock-result-ursa-major" to
            SolvingResult(
                id = "mock-result-ursa-major",
                originalImageUri = ursaMajor.imageUrl ?: "",
                solvingStatus = SolvingStatus.GETTING_MORE_DETAILS,
                createdAt = today.minus(1, ChronoUnit.DAYS),
            ),
        "mock-result-ursa-minor" to
            SolvingResult(
                id = "mock-result-ursa-minor",
                originalImageUri = ursaMinor.imageUrl ?: "",
                solvingStatus = SolvingStatus.FINDING_INTERESTING_INFO,
                createdAt = today.minus(1, ChronoUnit.DAYS).plusSeconds(100),
            ),
        "mock-result-draco" to
            SolvingResult(
                id = "mock-result-draco",
                originalImageUri = draco.imageUrl ?: "",
                solvingStatus = SolvingStatus.FAILURE,
                createdAt = today.minus(1, ChronoUnit.DAYS).plusSeconds(200),
            ),
        // History 3 results (3 days ago)
        "mock-result-leo" to
            SolvingResult(
                id = "mock-result-leo",
                originalImageUri = leo.imageUrl ?: "",
                solvingStatus = SolvingStatus.CANCELLED,
                createdAt = today.minus(3, ChronoUnit.DAYS),
            ),
        "mock-result-aquarius" to
            SolvingResult(
                id = "mock-result-aquarius",
                originalImageUri = aquarius.imageUrl ?: "",
                solvingStatus = SolvingStatus.QUEUED,
                createdAt = today.minus(3, ChronoUnit.DAYS).plusSeconds(100),
            ),
        // History 4 results (1 week ago)
        "mock-result-crux" to
            SolvingResult(
                id = "mock-result-crux",
                originalImageUri = crux.imageUrl ?: "",
                solvingStatus = SolvingStatus.IDENTIFYING_OBJECTS,
                createdAt = today.minus(7, ChronoUnit.DAYS),
            ),
    )
  }
}
