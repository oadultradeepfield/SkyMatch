package com.oadultradeepfield.skymatch.data.fake

import com.oadultradeepfield.skymatch.config.AppConfig
import com.oadultradeepfield.skymatch.domain.model.celestialobject.CelestialObject
import com.oadultradeepfield.skymatch.domain.model.celestialobject.DeepSkyObjectType
import com.oadultradeepfield.skymatch.domain.model.celestialobject.StarSpectralType
import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.domain.model.solve.IdentifiedObject
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingStatus
import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Fake implementation of [ISolveRepository] for testing.
 *
 * Simulates plate solving with configurable delays and failure rates.
 */
@Singleton
class FakeSolveRepository @Inject constructor() : ISolveRepository {
    private val mutex = Mutex()
    private val results = mutableMapOf<String, SolvingResult>()
    private val originalImageUris = mutableMapOf<String, String>()
    private val cancelledJobs = mutableSetOf<String>()

    private val networkDelayMs = AppConfig.Network.DEFAULT_DELAY_MS
    private val failureProbability = AppConfig.Fake.FAILURE_PROBABILITY
    private val observeFailureProbability = AppConfig.Fake.OBSERVE_FAILURE_PROBABILITY

    private val solvingTimeline = listOf(
        SolvingStatus.QUEUED to 1_000L,
        SolvingStatus.IDENTIFYING_OBJECTS to 2_000L,
        SolvingStatus.GETTING_MORE_DETAILS to 1_500L,
        SolvingStatus.FINDING_INTERESTING_INFO to 1_500L,
    )

    override suspend fun solve(imageByte: ByteArray, originalImageUri: String): String {
        delay(Random.nextLong(networkDelayMs))

        if (Random.nextDouble() < failureProbability) {
            throw IOException("Simulated network failure")
        }

        val jobId = UUID.randomUUID().toString()
        mutex.withLock {
            originalImageUris[jobId] = originalImageUri
            results[jobId] = createResult(jobId)
        }
        return jobId
    }

    override suspend fun cancelSolving(jobId: String) {
        delay(Random.nextLong(networkDelayMs))
        mutex.withLock {
            cancelledJobs.add(jobId)
            results[jobId]?.let { results[jobId] = it.copy(status = SolvingStatus.CANCELLED) }
        }
    }

    override fun observeSolving(jobId: String): Flow<SolvingResult?> = flow {
        val initial = mutex.withLock { results[jobId] }
        emit(initial)
        if (initial == null) return@flow

        for ((status, delayMs) in solvingTimeline) {
            if (mutex.withLock { jobId in cancelledJobs }) {
                emit(mutex.withLock { results[jobId] })
                return@flow
            }

            delay(delayMs)

            if (Random.nextDouble() < observeFailureProbability) {
                mutex.withLock {
                    results[jobId]?.let {
                        val failed = it.copy(status = SolvingStatus.FAILURE)
                        results[jobId] = failed
                        emit(failed)
                    }
                }
                return@flow
            }

            if (mutex.withLock { jobId in cancelledJobs }) {
                emit(mutex.withLock { results[jobId] })
                return@flow
            }

            mutex.withLock {
                results[jobId]?.let {
                    val updated = it.copy(status = status)
                    results[jobId] = updated
                    emit(updated)
                }
            }
        }

        if (mutex.withLock { jobId in cancelledJobs }) {
            emit(mutex.withLock { results[jobId] })
            return@flow
        }

        mutex.withLock {
            results[jobId]?.let {
                val success = it.copy(
                    status = SolvingStatus.SUCCESS,
                    annotatedImageUri = originalImageUris[jobId] ?: "",
                    identifiedObjects = createFakeObjects(),
                )
                results[jobId] = success
                emit(success)
            }
        }
    }

    private fun createResult(jobId: String): SolvingResult {
        return SolvingResult(
            id = jobId,
            status = SolvingStatus.QUEUED,
            originalImageUri = originalImageUris[jobId] ?: "",
        )
    }

    override fun getResult(jobId: String): SolvingResult? {
        return results[jobId]
    }

    private fun createFakeObjects(): List<IdentifiedObject> {
        val orion = Constellation(latinName = "Orion", englishName = "The Hunter")
        val andromeda = Constellation(latinName = "Andromeda", englishName = "The Chained Maiden")
        val taurus = Constellation(latinName = "Taurus", englishName = "The Bull")
        val lyra = Constellation(latinName = "Lyra", englishName = "The Harp")

        return listOf(
            IdentifiedObject(
                celestialObject = CelestialObject.Star(
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
                celestialObject = CelestialObject.Star(
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
                celestialObject = CelestialObject.DeepSkyObject(
                    identifier = "M31",
                    name = "Andromeda Galaxy",
                    constellation = andromeda,
                    type = DeepSkyObjectType.GALAXY,
                ),
                xCoordinate = 120.0,
                yCoordinate = 88.0,
            ),
            IdentifiedObject(
                celestialObject = CelestialObject.DeepSkyObject(
                    identifier = "M45",
                    name = "Pleiades",
                    constellation = taurus,
                    type = DeepSkyObjectType.OPEN_CLUSTER,
                ),
                xCoordinate = 260.4,
                yCoordinate = 150.7,
            ),
            IdentifiedObject(
                celestialObject = CelestialObject.Star(
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
