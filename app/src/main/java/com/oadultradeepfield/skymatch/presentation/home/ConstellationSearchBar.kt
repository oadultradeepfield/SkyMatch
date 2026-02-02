package com.oadultradeepfield.skymatch.presentation.home

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstellationSearchBar(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
) {
  val textFieldState = rememberTextFieldState()

  SearchBarDefaults.InputField(
      modifier = modifier,
      state = textFieldState,
      onSearch = { onSearchClick() },
      expanded = false,
      onExpandedChange = { if (it) onSearchClick() },
      placeholder = { Text("Type a constellation name...") },
      leadingIcon = {
        if (showBackButton && onBackClick != null) {
          IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        } else {
          Icon(Icons.Filled.Search, contentDescription = null)
        }
      },
  )
}

@Preview(showBackground = true, name = "Entry Point")
@Composable
fun PreviewConstellationSearchBarEntry() {
  AppTheme(dynamicColor = false) { ConstellationSearchBar(onSearchClick = {}) }
}

@Preview(showBackground = true, name = "With Back Button")
@Composable
fun PreviewConstellationSearchBarWithBack() {
  AppTheme(dynamicColor = false) {
    ConstellationSearchBar(onSearchClick = {}, showBackButton = true, onBackClick = {})
  }
}
