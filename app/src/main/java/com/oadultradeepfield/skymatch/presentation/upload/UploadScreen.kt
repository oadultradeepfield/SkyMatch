package com.oadultradeepfield.skymatch.presentation.upload

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun UploadScreen(
    onNavigateToSolving: (List<String>) -> Unit,
    viewModel: UploadViewModel = hiltViewModel(),
) {
  Text(text = "Upload Here!")
}

@Preview
@Composable
fun PreviewUploadScreen() {
  UploadScreen(onNavigateToSolving = {})
}
