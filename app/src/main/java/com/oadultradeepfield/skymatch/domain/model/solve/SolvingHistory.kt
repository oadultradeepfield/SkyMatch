package com.oadultradeepfield.skymatch.domain.model.solve

import java.time.Instant

/**
 * Represents the history of solving celestial images, containing a list of solving results.
 *
 * @param id The unique identifier for the history.
 * @param solvingResults The list of solving results, each containing a list of identified celestial
 *   objects.
 * @param createdAt The timestamp when the history was created.
 */
data class SolvingHistory(
    val id: String,
    val solvingResults: List<SolvingResult>,
    val createdAt: Instant = Instant.now(),
)
