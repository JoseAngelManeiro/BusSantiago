package org.galio.bussantiago.features.incidences.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.features.incidences.IncidencesViewModel

@Composable
fun IncidencesScreenWithAppBar(
  lineId: Int,
  viewModel: IncidencesViewModel
) {
  val incidencesState: Resource<List<String>>
  by viewModel.incidences.observeAsState(initial = Resource.loading())

  // Trigger loading data when composable first enters the screen
  LaunchedEffect(lineId) {
    viewModel.loadIncidences(lineId)
  }

  Box {
    when (incidencesState.status) {
      Status.LOADING -> {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }
      else -> {
        IncidencesScreen(incidences = incidencesState.data.orEmpty())
      }
    }
  }
}
