package org.galio.bussantiago.common.di

import org.galio.bussantiago.ui.incidences.GetLineIncidences
import org.galio.bussantiago.ui.incidences.IncidencesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val incidencesModule = module {
  factory { GetLineIncidences(lineDetailsRepository = get()) }
  viewModel {
    IncidencesViewModel(
      executor = get(),
      getLineIncidences = get()
    )
  }
}
