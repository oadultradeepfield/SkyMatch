package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.domain.model.celestialobject.CelestialObject
import com.oadultradeepfield.skymatch.domain.model.celestialobject.DeepSkyObjectType
import com.oadultradeepfield.skymatch.domain.model.celestialobject.StarSpectralType
import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.domain.model.solve.IdentifiedObject
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingStatus
import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

/**
 * Fake implementation of [ISolveRepository] for testing.
 *
 * Simulates plate solving with configurable delays and failure rates. All data is stored in-memory
 * and does not persist across instances. Image URIs are returned as fake content URIs - actual
 * image handling is done by upper layers.
 */
@Singleton
class FakeSolveRepository @Inject constructor() : ISolveRepository {
  private val results = mutableMapOf<String, SolvingResult>()
  private val cancelledJobs = mutableSetOf<String>()

  var networkDelayMs: Long = 500L
  var failureProbability: Double = 0.10
  var observeFailureProbability: Double = 0.20

  /** Simulated solving timeline with delays between status changes. */
  private val solvingTimeline =
      listOf(
          SolvingStatus.QUEUED to 1_000L,
          SolvingStatus.IDENTIFYING_OBJECTS to 2_000L,
          SolvingStatus.GETTING_MORE_DETAILS to 1_500L,
          SolvingStatus.FINDING_INTERESTING_INFO to 1_500L,
      )

  override suspend fun solve(imageByte: ByteArray): String {
    delay(Random.nextLong(networkDelayMs))

    if (Random.nextDouble() < failureProbability) {
      throw IOException("Simulated network failure")
    }

    val jobId = UUID.randomUUID().toString()
    results[jobId] = createResult(jobId, SolvingStatus.QUEUED)
    return jobId
  }

  override suspend fun cancelSolving(jobId: String) {
    delay(Random.nextLong(networkDelayMs))
    cancelledJobs.add(jobId)
    results[jobId] = createResult(jobId, SolvingStatus.CANCELLED)
  }

  override fun observeSolving(jobId: String): Flow<SolvingResult?> = flow {
    val initial = results[jobId]
    emit(initial)
    if (initial == null) return@flow

    for ((status, delayMs) in solvingTimeline) {
      if (jobId in cancelledJobs) return@flow

      delay(delayMs)

      if (Random.nextDouble() < observeFailureProbability) {
        val failed = createResult(jobId, SolvingStatus.FAILURE)
        results[jobId] = failed
        emit(failed)
        return@flow
      }

      if (jobId in cancelledJobs) return@flow

      val updated = createResult(jobId, status)
      results[jobId] = updated
      emit(updated)
    }

    if (jobId in cancelledJobs) return@flow

    val success = createResult(jobId, SolvingStatus.SUCCESS, includeObjects = true)
    results[jobId] = success
    emit(success)
  }

  /** Creates a fake solving result with optional identified objects. */
  private fun createResult(
      jobId: String,
      status: SolvingStatus,
      includeObjects: Boolean = false,
  ): SolvingResult {
    return SolvingResult(
        id = jobId,
        solvingStatus = status,
        originalImageUri = "content://fake/$jobId",
        annotatedImageUri = if (includeObjects) "content://fake/$jobId/annotated" else null,
        identifiedObjects = if (includeObjects) createFakeObjects() else null,
    )
  }

  /** Creates a fake list of identified objects. */
  private fun createFakeObjects(): List<IdentifiedObject> {
    val orion = Constellation(latinName = "Orion", englishName = "The Hunter")
    val andromeda = Constellation(latinName = "Andromeda", englishName = "The Chained Maiden")
    val taurus = Constellation(latinName = "Taurus", englishName = "The Bull")
    val lyra = Constellation(latinName = "Lyra", englishName = "The Harp")

    return listOf(
        IdentifiedObject(
            celestialObject =
                CelestialObject.Star(
                    identifier = "HIP 27989",
                    name = "Betelgeuse",
                    constellation = orion,
                    visualMagnitude = 0.42,
                    spectralType = StarSpectralType.M,
                    distanceParsecs = 152.0,
                ),
            xCoordinate = 412.6,
            yCoordinate = 238.1,
        ),
        IdentifiedObject(
            celestialObject =
                CelestialObject.Star(
                    identifier = "HIP 24436",
                    name = "Rigel",
                    constellation = orion,
                    visualMagnitude = 0.12,
                    spectralType = StarSpectralType.B,
                    distanceParsecs = 264.0,
                ),
            xCoordinate = 530.2,
            yCoordinate = 415.9,
        ),
        IdentifiedObject(
            celestialObject =
                CelestialObject.DeepSkyObject(
                    identifier = "M31",
                    name = "Andromeda Galaxy",
                    constellation = andromeda,
                    type = DeepSkyObjectType.GALAXY,
                ),
            xCoordinate = 120.0,
            yCoordinate = 88.0,
        ),
        IdentifiedObject(
            celestialObject =
                CelestialObject.DeepSkyObject(
                    identifier = "M45",
                    name = "Pleiades",
                    constellation = taurus,
                    type = DeepSkyObjectType.OPEN_CLUSTER,
                ),
            xCoordinate = 260.4,
            yCoordinate = 150.7,
        ),
        IdentifiedObject(
            celestialObject =
                CelestialObject.Star(
                    identifier = "HIP 91262",
                    name = null,
                    constellation = lyra,
                    visualMagnitude = 0.03,
                    spectralType = StarSpectralType.A,
                    distanceParsecs = 7.68,
                ),
            xCoordinate = 760.8,
            yCoordinate = 92.3,
        ),
    )
  }
}
