package com.oadultradeepfield.skymatch.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
  val state by viewModel.state.collectAsState()
  val textFieldState = remember { TextFieldState() }

  LaunchedEffect(textFieldState) {
    snapshotFlow { textFieldState.text.toString() }
        .collect { query -> viewModel.dispatch(SearchIntent.UpdateQuery(query)) }
  }

  SearchScreenContent(
      state = state,
      textFieldState = textFieldState,
      onNavigateBack = onNavigateBack,
      onClearQuery = {
        textFieldState.edit { replace(0, length, "") }
        viewModel.dispatch(SearchIntent.ClearQuery)
      },
      modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    state: SearchState,
    textFieldState: TextFieldState,
    onNavigateBack: () -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier,
) {
  Column(modifier = modifier.fillMaxSize()) {
    SearchBar(
        inputField = {
          SearchBarDefaults.InputField(
              state = textFieldState,
              onSearch = {},
              expanded = true,
              onExpandedChange = {},
              placeholder = { Text("Search constellations...") },
              leadingIcon = {
                IconButton(onClick = onNavigateBack) {
                  Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
              },
              trailingIcon =
                  if (state.query.isNotEmpty()) {
                    {
                      IconButton(onClick = onClearQuery) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                      }
                    }
                  } else {
                    null
                  },
          )
        },
        expanded = true,
        onExpandedChange = { if (!it) onNavigateBack() },
        modifier = Modifier.fillMaxWidth(),
    ) {
      SearchResultContent(state = state)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewSearchScreen() {
  AppTheme(dynamicColor = false) {
    SearchScreenContent(
        state =
            SearchState(
                constellations =
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
            ),
        textFieldState = TextFieldState(),
        onNavigateBack = {},
        onClearQuery = {},
    )
  }
}
