package com.oadultradeepfield.skymatch.domain.usecase.solve

import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

/**
 * Use case for canceling the solving of images for the given job IDs.
 *
 * @param repo The repository for canceling image solving.
 */
class CancelSolvingUseCase @Inject constructor(
    private val repo: ISolveRepository
) {
    /**
     * Cancels the solving of images for the given job IDs.
     *
     * @param jobIds The list of job IDs to cancel.
     * @return A list of results, where each result is either a success or a failure with an exception.
     */
    suspend operator fun invoke(
        jobIds: List<String>,
    ): List<Result<Unit>> = coroutineScope {
        jobIds
            .map { jobId ->
                async {
                    runCatching { repo.cancelSolving(jobId) }
                }
            }
            .awaitAll()
    }
}