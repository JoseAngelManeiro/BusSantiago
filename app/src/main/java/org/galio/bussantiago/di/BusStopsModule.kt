package org.galio.bussantiago.di

import org.galio.bussantiago.features.stops.BusStopsListViewModel
import org.galio.bussantiago.domain.interactor.GetLineBusStops
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val busStopsModule = module {
  factory {
    GetLineBusStops(
      lineDetailsRepository = get()
    )
  }
  viewModel { BusStopsListViewModel(executor = get(), getLineBusStops = get()) }
}
