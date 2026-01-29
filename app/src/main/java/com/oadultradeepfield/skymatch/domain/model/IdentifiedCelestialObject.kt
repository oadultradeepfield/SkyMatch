package com.oadultradeepfield.skymatch.domain.model

/**
 * Represents a celestial object that has been identified with its coordinates.
 *
 * @param celestialObject The celestial object that has been identified.
 * @param xCoordinate The x-coordinate of the celestial object in the image.
 * @param yCoordinate The y-coordinate of the celestial object in the image.
 */
data class IdentifiedCelestialObject(
    val celestialObject: CelestialObject,
    val xCoordinate: Double,
    val yCoordinate: Double,
)
