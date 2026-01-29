package com.oadultradeepfield.skymatch.domain.model

import android.media.Image
import java.time.Instant

/**
 * Represents the result of solving a celestial image, containing a list of identified celestial objects.
 *
 * @param id The unique identifier for the solved result.
 * @param identifiedCelestialObjects The list of identified celestial objects with their coordinates.
 * @param originalImage The original image that was solved.
 * @param annotatedImage The image with annotated celestial objects.
 * @param createdAt The timestamp when the result was created.
 */
data class SolvedResult(
    val id: String,
    val identifiedCelestialObjects: List<IdentifiedCelestialObject>,
    val originalImage: Image,
    val annotatedImage: Image,
    val createdAt: Instant = Instant.now(),
)
