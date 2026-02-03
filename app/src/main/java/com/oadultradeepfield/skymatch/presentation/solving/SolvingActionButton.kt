package com.oadultradeepfield.skymatch.presentation.solving

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SolvingActionButton(
    isCancellable: Boolean,
    onCancel: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
  if (isCancellable) {
    OutlinedButton(
        onClick = onCancel,
        modifier = modifier.fillMaxWidth(0.6f),
    ) {
      Text("Cancel")
    }
  } else {
    Button(
        onClick = onDelete,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
            ),
        modifier = modifier.fillMaxWidth(0.6f),
    ) {
      Text("Delete")
    }
  }
}
