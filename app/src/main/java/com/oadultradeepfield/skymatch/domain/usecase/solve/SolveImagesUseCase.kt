package com.oadultradeepfield.skymatch.domain.usecase.solve

import com.oadultradeepfield.skymatch.di.DispatcherProvider
import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/** Use case for solving images to identify celestial objects in parallel. */
class SolveImagesUseCase @Inject constructor(
    private val repo: ISolveRepository,
    private val dispatchers: DispatcherProvider,
) {
    /** Solves a list of images and returns results for each. */
    suspend operator fun invoke(
        images: List<Pair<ByteArray, String>>,
    ): List<Result<String>> = withContext(dispatchers.io) {
        coroutineScope {
            images.map { (bytes, uri) ->
                async { runCatching { repo.solve(bytes, uri) } }
            }.awaitAll()
        }
    }
}
