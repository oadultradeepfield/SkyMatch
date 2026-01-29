package com.oadultradeepfield.skymatch.domain.model.celestialobject

/**
 * Represents the type of deep sky object.
 *
 * @param displayName The human-readable name of the deep sky object type.
 */
enum class DeepSkyObjectType(val displayName: String) {
    OPEN_CLUSTER("Open Cluster"),
    GLOBULAR_CLUSTER("Globular Cluster"),
    GALAXY("Galaxy"),
    NEBULA("Nebula"),
    SUPERNOVA("Supernova"),
}