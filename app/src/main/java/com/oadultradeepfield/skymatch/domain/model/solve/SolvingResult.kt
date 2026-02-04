package com.oadultradeepfield.skymatch.domain.model.solve

import java.time.Instant

/**
 * Represents the result of solving a celestial image, containing a list of identified celestial
 * objects.
 *
 * @param id The unique identifier for the solved result.
 * @param identifiedObjects The list of identified celestial objects with their coordinates. Null if
 *   the solving process is not yet complete.
 * @param originalImageUri The local content URI of the original image that was solved.
 * @param annotatedImageUrl The remote URL of the image with annotated celestial objects. Null if
 *   the solving process is not yet complete.
 * @param status The status of the solving process.
 * @param createdAt The timestamp when the result was created.
 */
data class SolvingResult(
    val id: String,
    val identifiedObjects: List<IdentifiedObject>? = null,
    val originalImageUri: String,
    val annotatedImageUrl: String? = null,
    val status: SolvingStatus,
    val createdAt: Instant = Instant.now(),
)
