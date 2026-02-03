package com.oadultradeepfield.skymatch.presentation.solving

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingStatus

@Composable
fun SolvingStatusText(
    status: SolvingStatus,
    modifier: Modifier = Modifier,
) {
  val isInProgress = status.isCancellable()

  if (isInProgress) {
    BlinkingStatusText(text = status.displayName, modifier = modifier)
  } else {
    StaticStatusText(status = status, modifier = modifier)
  }
}

@Composable
private fun BlinkingStatusText(
    text: String,
    modifier: Modifier = Modifier,
) {
  val infiniteTransition = rememberInfiniteTransition(label = "blink")
  val alpha by
      infiniteTransition.animateFloat(
          initialValue = 1f,
          targetValue = 0.3f,
          animationSpec =
              infiniteRepeatable(
                  animation = tween(500),
                  repeatMode = RepeatMode.Reverse,
              ),
          label = "alpha",
      )

  Text(
      text = text,
      style = MaterialTheme.typography.bodyLarge,
      fontWeight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.primary,
      textAlign = TextAlign.Center,
      modifier = modifier.alpha(alpha),
  )
}

@Composable
private fun StaticStatusText(
    status: SolvingStatus,
    modifier: Modifier = Modifier,
) {
  val textColor =
      when (status) {
        SolvingStatus.SUCCESS -> MaterialTheme.colorScheme.primary
        SolvingStatus.FAILURE -> MaterialTheme.colorScheme.error
        SolvingStatus.CANCELLED -> MaterialTheme.colorScheme.onSurfaceVariant
        else -> MaterialTheme.colorScheme.onSurface
      }

  Text(
      text = status.displayName,
      style = MaterialTheme.typography.bodyLarge,
      fontWeight = FontWeight.Medium,
      color = textColor,
      textAlign = TextAlign.Center,
      modifier = modifier,
  )
}
