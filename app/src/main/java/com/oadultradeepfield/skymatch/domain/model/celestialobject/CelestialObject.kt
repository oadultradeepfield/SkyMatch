package com.oadultradeepfield.skymatch.domain.model.celestialobject

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation

/**
 * Represents a celestial object in the sky.
 *
 * @param identifier A unique identifier for the celestial object.
 * @param name The optional name of the celestial object.
 * @param constellation The constellation to which the celestial object belongs.
 */
sealed class CelestialObject(
    open val identifier: String,
    open val name: String? = null,
    open val constellation: Constellation
) {
    /**
     * Represents a star in the sky.
     *
     * @param identifier A unique identifier for the star.
     * @param name The optional name of the star.
     * @param constellation The constellation to which the star belongs.
     * @param visualMagnitude The visual magnitude of the star.
     * @param spectralType The spectral type of the star.
     * @param distanceParsecs The distance of the star from the observer in parsecs.
     */
    data class Star(
        override val identifier: String,
        override val name: String? = null,
        override val constellation: Constellation,
        val visualMagnitude: Double,
        val spectralType: StarSpectralType,
        val distanceParsecs: Double,
    ) : CelestialObject(identifier, name, constellation)

    /**
     * Represents a deep sky object in the sky.
     *
     * @param identifier A unique identifier for the deep sky object.
     * @param name The optional name of the deep sky object.
     * @param constellation The constellation to which the deep sky object belongs.
     * @param type The type of the deep sky object.
     */
    data class DeepSkyObject(
        override val identifier: String,
        override val name: String? = null,
        override val constellation: Constellation,
        val type: DeepSkyObjectType,
    ) : CelestialObject(identifier, name, constellation)
}