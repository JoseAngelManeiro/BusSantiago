package org.galio.bussantiago.di

import org.galio.bussantiago.domain.interactor.GetLineIncidences
import org.galio.bussantiago.features.incidences.IncidencesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val incidencesModule = module {
  factory {
    GetLineIncidences(
      lineDetailsRepository = get()
    )
  }
  viewModel {
    IncidencesViewModel(
      executor = get(),
      getLineIncidences = get()
    )
  }
}
