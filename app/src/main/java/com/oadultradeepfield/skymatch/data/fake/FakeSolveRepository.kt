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
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

/**
 * A fake implementation of [ISolveRepository] for testing purposes.
 */
@Singleton
class FakeSolveRepository @Inject constructor() : ISolveRepository {
    private val resultsByJobId = mutableMapOf<String, SolvingResult>()
    private val cancelledJobs = mutableSetOf<String>()

    private val maximumNetworkDelay = 1_000L
    private val pFailOnSolve = 0.10
    private val pFailDuringObserve = 0.20

    private val originalImageBaseUri = "content://fake/original"
    private val annotatedImageBaseUri = "content://fake/annotated"

    private val solvingTimeline: List<Pair<SolvingStatus, Long>> = listOf(
        SolvingStatus.QUEUED to 1_000L,
        SolvingStatus.IDENTIFYING_OBJECTS to 2_000L,
        SolvingStatus.GETTING_MORE_DETAILS to 1_500L,
        SolvingStatus.FINDING_INTERESTING_INFO to 1_500L,
    )

    override suspend fun solve(imageByte: ByteArray): String {
        simulateNetworkDelay()

        if (Random.nextDouble() < pFailOnSolve) {
            throw IOException("Fake network failure in solve")
        }

        val jobId = UUID.randomUUID().toString()

        resultsByJobId[jobId] = createResult(
            jobId = jobId,
            status = SolvingStatus.QUEUED,
            includeObjects = false
        )

        return jobId
    }

    override suspend fun cancelSolving(jobId: String) {
        simulateNetworkDelay()
        cancelledJobs.add(jobId)
        resultsByJobId[jobId] = createResult(
            jobId = jobId,
            status = SolvingStatus.CANCELLED,
            includeObjects = false
        )
    }

    override fun observeSolving(jobId: String): Flow<SolvingResult?> = flow {
        val initial = resultsByJobId[jobId]
        emit(initial)
        if (initial == null) return@flow

        for ((status, waitMs) in solvingTimeline) {
            if (isCancelled(jobId)) return@flow

            delay(waitMs)

            if (maybeFailDuringObserve(jobId)) return@flow
            if (isCancelled(jobId)) return@flow

            val updated = createResult(
                jobId = jobId,
                status = status,
                includeObjects = false
            )
            emitAndStore(jobId, updated)
        }

        if (isCancelled(jobId)) return@flow

        val success = createResult(
            jobId = jobId,
            status = SolvingStatus.SUCCESS,
            includeObjects = true
        )
        emitAndStore(jobId, success)
    }

    /**
     * Simulates a random network delay up to the maximum specified.
     */
    private suspend fun simulateNetworkDelay() {
        delay(Random.nextLong(maximumNetworkDelay))
    }

    /**
     * Checks whether a solving job with the specified ID has been cancelled.
     *
     * @param jobId The unique identifier for the solving job.
     * @return True if the job has been cancelled, false otherwise.
     */
    private fun isCancelled(jobId: String): Boolean = jobId in cancelledJobs

    /**
     * Emits a solving result to the flow collector and stores it in the results map.
     *
     * @param jobId The unique identifier for the solving job.
     * @param result The solving result to emit and store.
     */
    private suspend fun FlowCollector<SolvingResult?>.emitAndStore(
        jobId: String,
        result: SolvingResult,
    ) {
        resultsByJobId[jobId] = result
        emit(result)
    }

    /**
     * Simulates a random failure during observation of a solving job.
     *
     * @param jobId The unique identifier for the solving job.
     * @return True if a failure was simulated, false otherwise.
     */
    private suspend fun FlowCollector<SolvingResult?>.maybeFailDuringObserve(jobId: String): Boolean {
        if (Random.nextDouble() >= pFailDuringObserve) return false

        val failed = createResult(
            jobId = jobId,
            status = SolvingStatus.FAILURE,
            includeObjects = false
        )
        emitAndStore(jobId, failed)
        return true
    }

    /**
     * Creates a fake solving result with the specified parameters.
     *
     * @param jobId The unique identifier for the solving job.
     * @param status The status of the solving process.
     * @param includeObjects Whether to include identified objects in the result. Default is false.
     * @return A fake solving result.
     */
    private fun createResult(
        jobId: String,
        status: SolvingStatus,
        includeObjects: Boolean = false
    ): SolvingResult {
        return SolvingResult(
            id = jobId,
            solvingStatus = status,
            originalImageUri = "$originalImageBaseUri/$jobId",
            annotatedImageUri = if (includeObjects) "$annotatedImageBaseUri/$jobId" else null,
            identifiedObjects = if (includeObjects) fakeIdentifiedObjects() else null
        )
    }

    /**
     * Creates a fake list of identified objects.
     * Note: The list is generated by LLM, the correctness is not guaranteed.
     *
     * @return A list of fake identified objects.
     */
    private fun fakeIdentifiedObjects(): List<IdentifiedObject> {
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
            )
        )
    }
}