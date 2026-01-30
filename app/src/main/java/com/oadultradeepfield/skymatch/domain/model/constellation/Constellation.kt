package com.oadultradeepfield.skymatch.domain.model.constellation

/**
 * Represents a constellation with its Latin and English names.
 *
 * @param latinName The Latin name of the constellation.
 * @param englishName The English name of the constellation.
 * @param imageUrl The URL of the image representing the constellation. The image URL is only
 *   retrieved when the constellation is searched.
 */
data class Constellation(
    val latinName: String,
    val englishName: String,
    val imageUrl: String? = null,
)
