package com.oadultradeepfield.skymatch.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme
import com.oadultradeepfield.skymatch.util.formatRelativeDate

@Composable
fun HistoryGridItem(
    result: SolvingResult,
    modifier: Modifier = Modifier,
) {
  val placeholder = painterResource(R.drawable.placeholder)
  val context = LocalContext.current
  val dateLabel = formatRelativeDate(result.createdAt)

  Box(
      modifier = modifier.aspectRatio(5f / 8f).clip(RoundedCornerShape(8.dp)),
  ) {
    AsyncImage(
        model = ImageRequest.Builder(context).data(result.originalImageUri).crossfade(300).build(),
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
  }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHistoryGridItem() {
  AppTheme(dynamicColor = false) {
    HistoryGridItem(
        result = MockData.solvingResults.values.first(),
        modifier = Modifier.padding(16.dp),
    )
  }
}
