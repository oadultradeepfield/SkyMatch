package com.oadultradeepfield.skymatch.presentation.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    dismissText: String = "Cancel",
    isDestructive: Boolean = false,
) {
  AlertDialog(
      modifier = modifier,
      onDismissRequest = onDismiss,
      title = { Text(text = title) },
      text = { Text(text = message) },
      dismissButton = { TextButton(onClick = onDismiss) { Text(text = dismissText) } },
      confirmButton = {
        TextButton(
            onClick = onConfirm,
            colors =
                if (isDestructive) {
                  ButtonDefaults.textButtonColors(
                      contentColor = MaterialTheme.colorScheme.error,
                  )
                } else {
                  ButtonDefaults.textButtonColors()
                },
        ) {
          Text(text = confirmText)
        }
      },
  )
}
