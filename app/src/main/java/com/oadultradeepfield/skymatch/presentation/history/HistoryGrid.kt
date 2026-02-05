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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oadultradeepfield.skymatch.config.AppConfig
import com.oadultradeepfield.skymatch.data.fake.MockData
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.presentation.ui.modifier.fadeEdges
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun HistoryGrid(
    histories: List<SolvingHistory>,
    onScrolledChanged: (Boolean) -> Unit,
    onHistoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
  val backgroundColor = MaterialTheme.colorScheme.background
  val gridState = rememberLazyGridState()
  val isDarkTheme = isSystemInDarkTheme()

  val isScrolled by remember { derivedStateOf { gridState.canScrollBackward } }
  val topFadeAlpha by
      animateFloatAsState(
          targetValue = if (isScrolled && isDarkTheme) 1f else 0f,
          label = "topFadeAlpha",
      )

  LaunchedEffect(isScrolled) { onScrolledChanged(isScrolled) }

  Box(
      modifier =
          modifier
              .background(backgroundColor)
              .fadeEdges(
                  backgroundColor = backgroundColor,
                  topFadeAlpha = topFadeAlpha,
              ),
  ) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(AppConfig.UI.GRID_COLUMNS),
        contentPadding =
            PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = if (isDarkTheme) 18.dp else 0.dp,
                bottom = AppConfig.UI.BOTTOM_PADDING_DP.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
      items(items = histories, key = { it.id }) { history ->
        HistoryGridItem(history = history, onClick = { onHistoryClick(history.id) })
      }
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
    HistoryGrid(histories = sampleHistories, onScrolledChanged = {}, onHistoryClick = {})
  }
}
