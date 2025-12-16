package org.galio.bussantiago.features.incidences.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.galio.bussantiago.common.ErrorDialog
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.features.incidences.IncidencesUserInteractions

@Composable
fun IncidencesScreenContainer(
  incidencesState: Resource<List<Incidence>>,
  userInteractions: IncidencesUserInteractions
) {
  Box(modifier = Modifier.fillMaxSize()) {
    incidencesState.Fold(
      onLoading = {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      },
      onSuccess = { incidences ->
        IncidencesScreen(incidences = incidences)
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
