package com.oadultradeepfield.skymatch.domain.usecase.solve

import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * Use case for observing the solving progress of multiple celestial images.
 *
 * @param repo The repository for solving celestial images.
 */
class ObserveSolvingUseCase @Inject constructor(
    private val repo: ISolveRepository
) {
    /**
     * Observes the solving progress for multiple jobs.
     * Emits a new list whenever any job's status changes.
     *
     * @param jobIds The job IDs to observe.
     * @return A flow emitting the current state of all jobs.
     */
    operator fun invoke(jobIds: List<String>): Flow<List<SolvingResult>> {
        if (jobIds.isEmpty()) {
            return flowOf(emptyList())
        }

        val flows = jobIds.map { jobId -> repo.observeSolving(jobId) }

        return combine(flows) { results -> results.toList() }
    }
}