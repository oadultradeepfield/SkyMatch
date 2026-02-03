package com.oadultradeepfield.skymatch.presentation.home

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oadultradeepfield.skymatch.presentation.history.HistorySection
import com.oadultradeepfield.skymatch.presentation.history.HistoryViewModel
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme
import com.oadultradeepfield.skymatch.presentation.upload.UploadButton

@Composable
fun HomeScreen(
    onNavigateToUpload: (List<String>) -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
  val view = LocalView.current
  val historyState by historyViewModel.state.collectAsStateWithLifecycle()
  var isHistoryScrolled by remember { mutableStateOf(false) }

  // This code portion ensures that the status bar text and icon displayed on the home screen are
  // white in both light and dark themes, since the background image is always dark.
  DisposableEffect(Unit) {
    val window = (view.context as Activity).window
    val insetsController = WindowInsetsControllerCompat(window, view)
    insetsController.isAppearanceLightStatusBars = false

    onDispose { insetsController.isAppearanceLightStatusBars = true }
  }

  Box(modifier = modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize()) {
      HomeScreenBanner(
          onNavigateToSearch = onNavigateToSearch,
          showBottomFade = isHistoryScrolled,
          modifier = Modifier.fillMaxWidth().height(300.dp),
      )
      HistorySection(
          histories = historyState.histories,
          isLoading = historyState.isLoading,
          error = historyState.error,
          onScrolledChanged = { isHistoryScrolled = it },
          modifier = Modifier.fillMaxWidth().weight(1f),
      )
    }

    UploadButton(
        onImagesSelected = { uris -> onNavigateToUpload(uris.map { it.toString() }) },
        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 44.dp),
    )
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
  AppTheme(dynamicColor = false) { HomeScreen(onNavigateToUpload = {}, onNavigateToSearch = {}) }
}
