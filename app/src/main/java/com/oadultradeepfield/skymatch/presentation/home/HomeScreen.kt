package com.oadultradeepfield.skymatch.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme
import com.oadultradeepfield.skymatch.presentation.upload.UploadButton

@Composable
fun HomeScreen(
    onNavigateToUpload: (List<String>) -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
  Box(modifier = modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
      HomeScreenBanner(
          onNavigateToSearch = onNavigateToSearch,
          modifier = Modifier.fillMaxWidth().height(300.dp),
      )
      GallerySection(modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp))
      Spacer(modifier = Modifier.height(90.dp))
    }

    UploadButton(
        onImagesSelected = { uris -> onNavigateToUpload(uris.map { it.toString() }) },
        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
    )
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
  AppTheme(dynamicColor = false) { HomeScreen(onNavigateToUpload = {}, onNavigateToSearch = {}) }
}
