package org.galio.bussantiago.di

import org.galio.bussantiago.features.stops.list.BusStopsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val busStopsListModule = module {

  viewModel {
    BusStopsListViewModel(
      executor = get(),
      getLineBusStops = get()
    )
  }
}
