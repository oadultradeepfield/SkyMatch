package com.oadultradeepfield.skymatch.presentation.upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun UploadButton(
    onImagesSelected: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier,
) {
  val photoPicker =
      rememberLauncherForActivityResult(
          contract = PickMultipleVisualMedia(maxItems = 10),
          onResult = { uris: List<Uri> ->
            if (uris.isNotEmpty()) {
              onImagesSelected(uris)
            }
          },
      )

  UploadButtonContent(
      onClick = { photoPicker.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) },
      modifier = modifier,
  )
}

@Composable
fun UploadButtonContent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
  ExtendedFloatingActionButton(
      onClick = onClick,
      modifier = modifier,
      icon = {
        Icon(
            imageVector = Icons.Default.ImageSearch,
            contentDescription = "Choose images to run plate solving",
        )
      },
      text = { Text(text = "Uncover images", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
  )
}

@Preview(showBackground = true)
@Composable
fun PreviewUploadButton() {
  AppTheme(dynamicColor = false) { UploadButtonContent(onClick = {}) }
}
