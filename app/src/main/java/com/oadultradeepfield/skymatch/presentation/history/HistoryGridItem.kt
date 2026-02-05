package com.oadultradeepfield.skymatch.presentation.history

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.oadultradeepfield.skymatch.R
import com.oadultradeepfield.skymatch.data.fake.MockData
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingHistory
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme
import com.oadultradeepfield.skymatch.util.formatRelativeDate

@Composable
fun HistoryGridItem(
    history: SolvingHistory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
  val firstResult = history.solvingResults.firstOrNull() ?: return
  val placeholder = painterResource(R.drawable.placeholder)
  val context = LocalContext.current
  val dateLabel = formatRelativeDate(firstResult.createdAt)
  val isProcessing = history.solvingResults.any { it.status.isCancellable() }

  Box(
      modifier =
          modifier.aspectRatio(5f / 8f).clip(RoundedCornerShape(8.dp)).clickable(onClick = onClick),
  ) {
    AsyncImage(
        model =
            ImageRequest.Builder(context).data(firstResult.originalImageUri).crossfade(300).build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = placeholder,
        filterQuality = FilterQuality.Medium,
        modifier = Modifier.fillMaxWidth(),
    )

    Box(
        modifier =
            Modifier.fillMaxWidth()
                .fillMaxHeight(0.4f)
                .align(Alignment.BottomCenter)
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        ),
                )
                .padding(horizontal = 12.dp, vertical = 16.dp),
        contentAlignment = Alignment.BottomStart,
    ) {
      Text(
          text = dateLabel,
          style = MaterialTheme.typography.labelLarge,
          color = Color.White,
      )
    }

    if (isProcessing) {
      ProcessingOverlay()
    }
  }
}

@Composable
private fun ProcessingOverlay(modifier: Modifier = Modifier) {
  val infiniteTransition = rememberInfiniteTransition(label = "processing")
  val alpha by
      infiniteTransition.animateFloat(
          initialValue = 0.3f,
          targetValue = 0.6f,
          animationSpec =
              infiniteRepeatable(
                  animation = tween(800),
                  repeatMode = RepeatMode.Reverse,
              ),
          label = "processingAlpha",
      )

  Box(modifier = modifier.fillMaxSize().background(Color.Black.copy(alpha = alpha)))
}

@Preview(showBackground = true)
@Composable
private fun PreviewHistoryGridItem() {
  AppTheme(dynamicColor = false) {
    val sampleHistory =
        SolvingHistory(
            id = "preview",
            solvingResults = listOf(MockData.solvingResults.values.first()),
        )
    HistoryGridItem(
        history = sampleHistory,
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
  }
}
