package com.oadultradeepfield.skymatch.domain.model.celestialobject

import androidx.compose.ui.graphics.Color

/**
 * Represents the spectral type of a star.
 *
 * @param color The color associated with the spectral type.
 */
enum class StarSpectralType(val color: Color) {
    O(Color(0xFF9BB0FF)), // Blue
    B(Color(0xFFAABFFF)), // Blue-white
    A(Color(0xFFCAD7FF)), // White
    F(Color(0xFFF8F7FF)), // Yellow-white
    G(Color(0xFFFFF4EA)), // Yellow (Sun-like)
    K(Color(0xFFFFD2A1)), // Orange
    M(Color(0xFFFFCC6F)), // Red-orange
}