package com.oadultradeepfield.skymatch.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun ConstellationListItem(
    constellation: Constellation,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
  val placeholder = painterResource(R.drawable.placeholder)
  val context = LocalContext.current
  val isClickable = onClick != null

  ConstellationListItemLayout(
      latinName = constellation.latinName,
      englishName = constellation.englishName,
      image = {
        AsyncImage(
            model =
                ImageRequest.Builder(context).data(constellation.imageUrl).crossfade(300).build(),
            contentDescription = constellation.latinName,
            contentScale = ContentScale.Crop,
            placeholder = placeholder,
            error = placeholder,
            filterQuality = FilterQuality.Medium,
            modifier = Modifier.size(72.dp).clip(RoundedCornerShape(8.dp)),
        )
      },
      modifier =
          modifier.then(if (isClickable) Modifier.clickable(onClick = onClick!!) else Modifier),
      alpha = if (isClickable) 1f else 0.5f,
  )
}

@Composable
private fun ConstellationListItemLayout(
    latinName: String,
    englishName: String,
    image: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
) {
  Row(
      modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
  ) {
    image()

    Spacer(modifier = Modifier.width(16.dp))

    Column {
      Text(
          text = latinName,
          style = MaterialTheme.typography.headlineSmall,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
      )
      Text(
          text = englishName,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun PreviewConstellationListItem() {
  AppTheme(dynamicColor = false) {
    ConstellationListItem(
        constellation =
            Constellation(
                latinName = "Orion",
                englishName = "The Hunter",
                imageUrl = "",
            ),
        onClick = {},
    )
  }
}
