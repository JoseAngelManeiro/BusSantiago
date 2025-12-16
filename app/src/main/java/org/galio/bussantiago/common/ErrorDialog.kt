package org.galio.bussantiago.common

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.galio.bussantiago.R

@Composable
fun ErrorDialog(
  exception: Exception,
  onRetry: () -> Unit = {},
  onCancel: () -> Unit = {}
) {
  AlertDialog(
    onDismissRequest = { /* non-dismissable */ },
    title = { Text(text = exception.message.orEmpty()) },
    text = { Text(text = stringResource(R.string.what_want_to_do)) },
    confirmButton = {
      TextButton(onClick = onRetry) {
        Text(text = stringResource(R.string.retry))
      }
    },
    dismissButton = {
      TextButton(onClick = onCancel) {
        Text(text = stringResource(R.string.cancel))
      }
    }
  )
}
