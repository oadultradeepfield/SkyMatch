package com.oadultradeepfield.skymatch.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun SearchResultContent(
    state: SearchState,
    modifier: Modifier = Modifier,
) {
  SearchResultContentLayout(
      isLoading = state.isLoading,
      error = state.error,
      constellations = state.constellations,
      modifier = modifier,
  )
}

@Composable
private fun SearchResultContentLayout(
    isLoading: Boolean,
    error: String?,
    constellations: List<Constellation>,
    modifier: Modifier = Modifier,
) {
  when {
    isLoading ->
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          CircularProgressIndicator()
        }
    error != null -> ErrorContent(error = error, modifier = modifier)
    constellations.isEmpty() -> EmptyContent(modifier = modifier)
    else ->
        LazyColumn(modifier = modifier.fillMaxSize()) {
          items(constellations, key = { it.latinName }) { constellation ->
            ConstellationListItem(constellation = constellation)
            HorizontalDivider()
          }
        }
  }
}

@Composable
private fun ErrorContent(
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

@Composable
private fun EmptyContent(modifier: Modifier = Modifier) {
  Column(
      modifier = modifier.fillMaxSize().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    Icon(
        imageVector = Icons.Default.SearchOff,
        contentDescription = null,
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "No constellations found",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun PreviewSearchResultContentLoading() {
  AppTheme(dynamicColor = false) {
    SearchResultContentLayout(
        isLoading = true,
        error = null,
        constellations = emptyList(),
    )
  }
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun PreviewSearchResultContentError() {
  AppTheme(dynamicColor = false) {
    SearchResultContentLayout(
        isLoading = false,
        error = "Failed to load constellations. \nPlease try again.",
        constellations = emptyList(),
    )
  }
}

@Preview(showBackground = true, name = "Empty")
@Composable
private fun PreviewSearchResultContentEmpty() {
  AppTheme(dynamicColor = false) {
    SearchResultContentLayout(
        isLoading = false,
        error = null,
        constellations = emptyList(),
    )
  }
}

@Preview(showBackground = true, name = "Results")
@Composable
private fun PreviewSearchResultContentResults() {
  AppTheme(dynamicColor = false) {
    val sampleConstellations =
        listOf(
            Constellation(
                latinName = "Orion",
                englishName = "The Hunter",
                imageUrl = "",
            ),
            Constellation(
                latinName = "Ursa Major",
                englishName = "The Great Bear",
                imageUrl = "",
            ),
        )
    SearchResultContentLayout(
        isLoading = false,
        error = null,
        constellations = sampleConstellations,
    )
  }
}
