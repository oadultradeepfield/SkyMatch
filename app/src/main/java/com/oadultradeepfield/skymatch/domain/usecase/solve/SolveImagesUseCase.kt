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
   * Solves a list of images to identify celestial objects.
   *
   * @param images The list of pairs containing image bytes and their original URIs.
   * @return A list of results, where each result is either a success with the job ID or a failure
   *   with an exception.
   */
  suspend operator fun invoke(
      images: List<Pair<ByteArray, String>>,
  ): List<Result<String>> = coroutineScope {
    images.map { (bytes, uri) -> async { runCatching { repo.solve(bytes, uri) } } }.awaitAll()
  }
}
