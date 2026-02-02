package com.oadultradeepfield.skymatch.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun GallerySection(modifier: Modifier = Modifier) {
  Box(
      modifier =
          modifier.background(Color.Black).drawWithContent {
            drawRect(
                brush =
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = size.height * 0.15f,
                    )
            )
            drawContent()
            drawRect(
                brush =
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height * 0.85f,
                        endY = size.height,
                    )
            )
          }
  ) {
    Text(
        text = "Gallery Grid Goes Here",
        color = Color.White,
        modifier = Modifier.align(Alignment.Center),
    )
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewGallerySection() {
  AppTheme(dynamicColor = false) { GallerySection() }
}
