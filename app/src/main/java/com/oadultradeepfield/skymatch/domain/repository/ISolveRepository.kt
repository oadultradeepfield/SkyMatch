package com.oadultradeepfield.skymatch.domain.repository

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import kotlinx.coroutines.flow.Flow

/** Repository interface for solving celestial images and observing solving progress. */
interface ISolveRepository {
  /**
   * Solves a celestial image and returns the job id.
   *
   * @param imageByte The byte array representation of the celestial image to be solved.
   * @return The job id of the solving process.
   */
  suspend fun solve(imageByte: ByteArray): String

  /**
   * Cancels the solving process for a celestial image with the specified job id.
   *
   * @param jobId The job id of the solving process to be canceled.
   */
  suspend fun cancelSolving(jobId: String)

  /**
   * Observes the solving progress of a celestial image.
   *
   * @param jobId The job id of the solving process.
   * @return A flow of solving results updates.
   */
  fun observeSolving(jobId: String): Flow<SolvingResult?>
}
