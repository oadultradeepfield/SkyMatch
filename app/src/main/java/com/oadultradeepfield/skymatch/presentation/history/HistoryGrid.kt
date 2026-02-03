package com.oadultradeepfield.skymatch.presentation.history

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oadultradeepfield.skymatch.data.fake.MockData
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun HistoryGrid(
    histories: List<SolvingHistory>,
    onScrolledChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
  val thumbnails = histories.mapNotNull { it.solvingResults.firstOrNull() }
  val backgroundColor = MaterialTheme.colorScheme.background
  val gridState = rememberLazyGridState()
  val isDarkTheme = isSystemInDarkTheme()

  // Show top fade only when scrolled away from top, so the fade doesn't appear at initial state
  // This UI design is only pretty in the dark theme
  val isScrolled by remember { derivedStateOf { gridState.canScrollBackward } }
  val topFadeAlpha by
      animateFloatAsState(
          targetValue = if (isScrolled && isDarkTheme) 1f else 0f,
          label = "topFadeAlpha",
      )

  // Notify parent when scroll state changes
  LaunchedEffect(isScrolled) { onScrolledChanged(isScrolled) }

  Box(
      modifier =
          modifier.background(backgroundColor).drawWithContent {
            drawContent()
            if (topFadeAlpha > 0f) {
              drawRect(
                  brush =
                      Brush.verticalGradient(
                          colors = listOf(backgroundColor, Color.Transparent),
                          startY = 0f,
                          endY = size.height * 0.05f,
                      ),
                  alpha = topFadeAlpha,
              )
            }
            drawRect(
                brush =
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, backgroundColor, backgroundColor),
                        startY = size.height * 0.8f,
                        endY = size.height,
                    ),
            )
          },
  ) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding =
            PaddingValues(
                start = 12.dp,
                end = 12.dp,
                // This is an internal padding that would not overlay on top of the content
                top = if (isDarkTheme) 18.dp else 0.dp,
                bottom = 120.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
      items(items = thumbnails, key = { it.id }) { result -> HistoryGridItem(result = result) }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHistoryGrid() {
  AppTheme(dynamicColor = false) {
    val sampleHistories =
        MockData.initialHistories.map { historyData ->
          SolvingHistory(
              id = historyData.id,
              solvingResults = historyData.resultIds.mapNotNull { MockData.solvingResults[it] },
              createdAt = historyData.createdAt,
          )
        }
    HistoryGrid(histories = sampleHistories, onScrolledChanged = {})
  }
}
