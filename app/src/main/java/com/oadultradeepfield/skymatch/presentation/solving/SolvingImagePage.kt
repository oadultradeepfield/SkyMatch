package com.oadultradeepfield.skymatch.presentation.solving

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult
import com.oadultradeepfield.skymatch.domain.model.solve.SolvingStatus
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun SolvingImagePage(
    item: SolvingResult,
    pageIndex: Int,
    totalPages: Int,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
  Box(modifier = modifier.fillMaxSize()) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
          text = "${pageIndex + 1}/$totalPages",
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
      )

      SolvingStatusText(status = item.solvingStatus)

      Box(
          modifier = Modifier.weight(1f).fillMaxWidth(),
          contentAlignment = Alignment.Center,
      ) {
        SolvingImageViewer(
            imageUri = item.originalImageUri,
            pageIndex = pageIndex,
            totalPages = totalPages,
            onPreviousPage = onPreviousPage,
            onNextPage = onNextPage,
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      SolvingActionButton(
          isCancellable = item.solvingStatus.isCancellable(),
          onCancel = onCancel,
          onDelete = onDelete,
      )

      Spacer(modifier = Modifier.height(32.dp))
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSolvingImagePageInProgress() {
  AppTheme(dynamicColor = false) {
    SolvingImagePage(
        item =
            SolvingResult(
                id = "job-1",
                originalImageUri = "",
                solvingStatus = SolvingStatus.IDENTIFYING_OBJECTS,
            ),
        pageIndex = 0,
        totalPages = 3,
        onPreviousPage = {},
        onNextPage = {},
        onCancel = {},
        onDelete = {},
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSolvingImagePageCompleted() {
  AppTheme(dynamicColor = false) {
    SolvingImagePage(
        item =
            SolvingResult(
                id = "job-1",
                originalImageUri = "",
                solvingStatus = SolvingStatus.SUCCESS,
            ),
        pageIndex = 1,
        totalPages = 3,
        onPreviousPage = {},
        onNextPage = {},
        onCancel = {},
        onDelete = {},
    )
  }
}
