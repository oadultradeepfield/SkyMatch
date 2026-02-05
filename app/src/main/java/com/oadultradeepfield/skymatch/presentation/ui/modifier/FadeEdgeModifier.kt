package com.oadultradeepfield.skymatch.presentation.ui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/** Adds fade edges to a composable for visual polish on scrollable content. */
fun Modifier.fadeEdges(
    backgroundColor: Color,
    topFadeAlpha: Float = 0f,
    topFadeRatio: Float = 0.05f,
    bottomFadeRatio: Float = 0.2f,
): Modifier =
    this.drawWithContent {
      drawContent()

      if (topFadeAlpha > 0f) {
        drawRect(
            brush =
                Brush.verticalGradient(
                    colors = listOf(backgroundColor, Color.Transparent),
                    startY = 0f,
                    endY = size.height * topFadeRatio,
                ),
            alpha = topFadeAlpha,
        )
      }
      drawRect(
          brush =
              Brush.verticalGradient(
                  colors = listOf(Color.Transparent, backgroundColor, backgroundColor),
                  startY = size.height * (1 - bottomFadeRatio),
                  endY = size.height,
              ),
      )
    }
