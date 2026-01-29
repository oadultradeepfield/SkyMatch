package com.oadultradeepfield.skymatch.domain.model

import java.time.Instant

/**
 * Represents the result of solving a celestial image, containing a list of identified celestial objects.
 *
 * @param id The unique identifier for the solved result.
 * @param identifiedCelestialObjects The list of identified celestial objects with their coordinates.
 * @param createdAt The timestamp when the result was created.
 */
data class SolvedResult(
    val id: String,
    val identifiedCelestialObjects: List<IdentifiedCelestialObject>,
    val createdAt: Instant = Instant.now(),
)
