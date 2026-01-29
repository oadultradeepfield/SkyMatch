package com.oadultradeepfield.skymatch.domain.usecase.solve

import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

/**
 * Use case for solving images to identify celestial objects.
 *
 * @param repo The repository for solving images.
 */
class SolveImagesUseCase @Inject constructor(private val repo: ISolveRepository) {
  /**
   * Solves a list of image bytes to identify celestial objects.
   *
   * @param imageBytes The list of image bytes to be solved.
   * @return A list of results, where each result is either a success with the job ID or a failure
   *   with an exception.
   */
  suspend operator fun invoke(
      imageBytes: List<ByteArray>,
  ): List<Result<String>> = coroutineScope {
    imageBytes.map { bytes -> async { runCatching { repo.solve(bytes) } } }.awaitAll()
  }
}
