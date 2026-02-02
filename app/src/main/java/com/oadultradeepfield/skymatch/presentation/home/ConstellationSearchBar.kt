package com.oadultradeepfield.skymatch.presentation.home

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
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
    expanded: Boolean = false,
    textFieldState: TextFieldState? = null,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    showClearButton: Boolean = false,
    onClearClick: (() -> Unit)? = null,
) {
  val state = textFieldState ?: rememberTextFieldState()

  SearchBarDefaults.InputField(
      modifier = modifier,
      state = state,
      onSearch = { onSearchClick() },
      expanded = expanded,
      onExpandedChange = { if (it) onSearchClick() },
      placeholder = {
        Text(if (expanded) "Search constellations..." else "Type a constellation name...")
      },
      leadingIcon = {
        if (showBackButton && onBackClick != null) {
          IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        } else {
          Icon(Icons.Filled.Search, contentDescription = null)
        }
      },
      trailingIcon =
          if (expanded && showClearButton && onClearClick != null) {
            {
              IconButton(onClick = onClearClick) {
                Icon(Icons.Default.Close, contentDescription = "Clear")
              }
            }
          } else {
            null
          },
  )
}

@Preview(showBackground = true, name = "Entry Point")
@Composable
fun PreviewConstellationSearchBarEntry() {
  AppTheme(dynamicColor = false) { ConstellationSearchBar(onSearchClick = {}) }
}

@Preview(showBackground = true, name = "Expanded With Clear Button")
@Composable
fun PreviewConstellationSearchBarExpandedWithClear() {
  AppTheme(dynamicColor = false) {
    ConstellationSearchBar(
        onSearchClick = {},
        expanded = true,
        showBackButton = true,
        onBackClick = {},
        showClearButton = true,
        onClearClick = {},
    )
  }
}
