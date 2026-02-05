package com.oadultradeepfield.skymatch.domain.repository

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import kotlinx.coroutines.flow.Flow

/** Repository interface for solving celestial images and observing solving progress. */
interface ISolveRepository {
    /** Solves a celestial image and returns the job id. */
    suspend fun solve(imageByte: ByteArray, originalImageUri: String): String

    /** Cancels the solving process for a celestial image with the specified job id. */
    suspend fun cancelSolving(jobId: String)

    /** Observes the solving progress of a celestial image. */
    fun observeSolving(jobId: String): Flow<SolvingResult?>

    /** Retrieves a solving result by its job ID, or null if not found. */
    fun getResult(jobId: String): SolvingResult?
}
