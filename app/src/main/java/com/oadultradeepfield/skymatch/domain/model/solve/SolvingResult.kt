package com.oadultradeepfield.skymatch.domain.model.solve

import android.media.Image
import java.time.Instant

/**
 * Represents the result of solving a celestial image, containing a list of identified celestial objects.
 *
 * @param id The unique identifier for the solved result.
 * @param identifiedObjects The list of identified celestial objects with their coordinates.
 *                          Null if the solving process is not yet complete.
 * @param originalImage The original image that was solved.
 * @param annotatedImage The image with annotated celestial objects.
 *                          Null if the solving process is not yet complete.
 * @param solvingStatus The status of the solving process.
 * @param createdAt The timestamp when the result was created.
 */
data class SolvingResult(
    val id: String,
    val identifiedObjects: List<IdentifiedObject>? = null,
    val originalImage: Image,
    val annotatedImage: Image? = null,
    val solvingStatus: SolvingStatus,
    val createdAt: Instant = Instant.now(),
)