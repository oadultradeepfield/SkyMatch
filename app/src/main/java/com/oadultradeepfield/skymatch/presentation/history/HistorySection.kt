package com.oadultradeepfield.skymatch.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oadultradeepfield.skymatch.data.fake.MockData
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun HistorySection(
    histories: List<SolvingHistory>,
    isLoading: Boolean,
    error: String?,
    onScrolledChanged: (Boolean) -> Unit,
    onHistoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
  when {
    isLoading ->
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          CircularProgressIndicator()
        }
    error != null -> HistoryErrorContent(error = error, modifier = modifier)
    histories.isEmpty() || histories.all { it.solvingResults.isEmpty() } ->
        HistoryEmptyContent(modifier = modifier)
    else ->
        HistoryGrid(
            histories = histories,
            onScrolledChanged = onScrolledChanged,
            onHistoryClick = onHistoryClick,
            modifier = modifier,
        )
  }
}

@Composable
private fun HistoryErrorContent(
    error: String,
    modifier: Modifier = Modifier,
) {
  Column(
      modifier = modifier.fillMaxSize().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    Icon(
        imageVector = Icons.Default.ErrorOutline,
        contentDescription = null,
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.error,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = error,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
    )
  }
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun PreviewHistorySectionLoading() {
  AppTheme(dynamicColor = false) {
    HistorySection(
        histories = emptyList(),
        isLoading = true,
        error = null,
        onScrolledChanged = {},
        onHistoryClick = {},
    )
  }
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun PreviewHistorySectionError() {
  AppTheme(dynamicColor = false) {
    HistorySection(
        histories = emptyList(),
        isLoading = false,
        error = "Failed to load history.\nPlease try again.",
        onScrolledChanged = {},
        onHistoryClick = {},
    )
  }
}

@Preview(showBackground = true, name = "Empty")
@Composable
private fun PreviewHistorySectionEmpty() {
  AppTheme(dynamicColor = false) {
    HistorySection(
        histories = emptyList(),
        isLoading = false,
        error = null,
        onScrolledChanged = {},
        onHistoryClick = {},
    )
  }
}

@Preview(showBackground = true, name = "Content")
@Composable
private fun PreviewHistorySectionContent() {
  AppTheme(dynamicColor = false) {
    val sampleHistories =
        MockData.initialHistories.map { historyData ->
          SolvingHistory(
              id = historyData.id,
              solvingResults = historyData.resultIds.mapNotNull { MockData.solvingResults[it] },
              createdAt = historyData.createdAt,
          )
        }
    HistorySection(
        histories = sampleHistories,
        isLoading = false,
        error = null,
        onScrolledChanged = {},
        onHistoryClick = {},
    )
  }
}
