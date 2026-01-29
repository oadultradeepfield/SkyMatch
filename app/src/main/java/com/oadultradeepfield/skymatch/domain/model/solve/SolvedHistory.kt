package com.oadultradeepfield.skymatch.domain.model.solve

import java.time.Instant

/**
 * Represents the history of solved celestial images, containing a list of solved results.
 *
 * @param id The unique identifier for the history.
 * @param solvedResults The list of solved results, each containing a list of identified celestial objects.
 * @param createdAt The timestamp when the history was created.
 */
data class SolvedHistory(
    val id: String,
    val solvedResults: List<SolvedResult>,
    val createdAt: Instant = Instant.now(),
)