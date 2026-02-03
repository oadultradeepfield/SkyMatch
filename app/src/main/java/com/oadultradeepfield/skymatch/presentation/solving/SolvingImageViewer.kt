package com.oadultradeepfield.skymatch.presentation.solving

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.oadultradeepfield.skymatch.R

@Composable
fun SolvingImageViewer(
    imageUri: String,
    pageIndex: Int,
    totalPages: Int,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  val placeholder = painterResource(R.drawable.placeholder)

  Row(
      modifier = modifier.fillMaxSize(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    NavigationButton(
        onClick = onPreviousPage,
        enabled = pageIndex > 0,
        isNext = false,
    )

    AsyncImage(
        model = ImageRequest.Builder(context).data(imageUri).crossfade(300).build(),
        contentDescription = "Image ${pageIndex + 1}",
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = placeholder,
        filterQuality = FilterQuality.Medium,
        modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(16.dp)),
    )

    NavigationButton(
        onClick = onNextPage,
        enabled = pageIndex < totalPages - 1,
        isNext = true,
    )
  }
}

@Composable
private fun NavigationButton(
    onClick: () -> Unit,
    enabled: Boolean,
    isNext: Boolean,
    modifier: Modifier = Modifier,
) {
  IconButton(
      onClick = onClick,
      enabled = enabled,
      colors =
          IconButtonDefaults.iconButtonColors(
              contentColor = MaterialTheme.colorScheme.primary,
              disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
          ),
      modifier = modifier.size(48.dp),
  ) {
    Icon(
        imageVector =
            if (isNext) Icons.AutoMirrored.Filled.KeyboardArrowRight
            else Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        contentDescription = if (isNext) "Next" else "Previous",
        modifier = Modifier.size(32.dp),
    )
  }
}
