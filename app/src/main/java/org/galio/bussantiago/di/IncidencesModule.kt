package org.galio.bussantiago.di

import org.galio.bussantiago.features.incidences.IncidencesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val incidencesModule = module {
  viewModel { (lineId: Int) ->
    IncidencesViewModel(lineId = lineId, executor = get(), getLineIncidences = get())
  }
}
