package org.galio.bussantiago.features.information.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.galio.bussantiago.common.ErrorDialog
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.features.information.InformationUserInteractions

import androidx.compose.ui.platform.testTag

@Composable
fun InformationScreenContainer(
  informationState: Resource<String>,
  userInteractions: InformationUserInteractions
) {
  Box(modifier = Modifier.fillMaxSize()) {
    informationState.Fold(
      onLoading = {
        CircularProgressIndicator(
          modifier = Modifier
            .align(Alignment.Center)
            .testTag("LoadingIndicator")
        )
      },
      onSuccess = { information ->
        InformationScreen(information = information)
      },
      onError = { exception ->
        ErrorDialog(
          exception = exception,
          onRetry = userInteractions::onRetry,
          onCancel = userInteractions::onCancel
        )
      }
    )
  }
}
