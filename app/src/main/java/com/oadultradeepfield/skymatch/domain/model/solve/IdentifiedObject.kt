package com.oadultradeepfield.skymatch.domain.model.solve

import com.oadultradeepfield.skymatch.domain.model.celestialobject.CelestialObject

/**
 * Represents a celestial object that has been identified with its coordinates.
 *
 * @param celestialObject The celestial object that has been identified.
 * @param xCoordinate The x-coordinate of the celestial object in the image.
 * @param yCoordinate The y-coordinate of the celestial object in the image.
 */
data class IdentifiedObject(
    val celestialObject: CelestialObject,
    val xCoordinate: Double,
    val yCoordinate: Double,
)
