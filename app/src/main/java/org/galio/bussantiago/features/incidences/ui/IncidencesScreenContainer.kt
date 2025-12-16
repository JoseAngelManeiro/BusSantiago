package org.galio.bussantiago.features.incidences.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.galio.bussantiago.common.ErrorDialog
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.features.incidences.IncidencesViewModel

@Composable
fun IncidencesScreenContainer(
  lineId: Int,
  viewModel: IncidencesViewModel
) {
  val incidencesState: Resource<List<Incidence>>
    by viewModel.incidences.observeAsState(initial = Resource.loading())

  // Trigger loading data when composable first enters the screen
  LaunchedEffect(lineId) {
    viewModel.loadIncidences(lineId)
  }

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
          onRetry = { viewModel.loadIncidences(lineId) },
          onCancel = { viewModel.onCancelButtonClicked() }
        )
      }
    )
  }
}
