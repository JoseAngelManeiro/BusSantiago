package org.galio.bussantiago.di

import org.galio.bussantiago.domain.interactor.SearchBusStop
import org.galio.bussantiago.features.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
  factory { SearchBusStop(searchBusStopRepository = get()) }
  viewModel {
    SearchViewModel(
      executor = get(),
      searchBusStop = get()
    )
  }
}
