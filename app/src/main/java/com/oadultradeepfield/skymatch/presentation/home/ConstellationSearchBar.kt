package com.oadultradeepfield.skymatch.presentation.home

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
) {
  val textFieldState = rememberTextFieldState()

  SearchBarDefaults.InputField(
      modifier = modifier,
      state = textFieldState,
      onSearch = { onSearchClick() },
      expanded = false,
      onExpandedChange = { if (it) onSearchClick() },
      placeholder = { Text("Type a constellation name...") },
      leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
      trailingIcon = null,
  )
}

@Preview(showBackground = true)
@Composable
fun PreviewConstellationSearchBar() {
  AppTheme(dynamicColor = false) { ConstellationSearchBar(onSearchClick = {}) }
}
