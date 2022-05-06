package org.galio.bussantiago.di

import org.galio.bussantiago.features.stops.list.BusStopsListViewModel
import org.galio.bussantiago.domain.interactor.GetLineBusStops
import org.galio.bussantiago.features.stops.map.BusStopsMapViewModel
import org.galio.bussantiago.features.stops.map.LineMapModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val busStopsModule = module {

  factory { GetLineBusStops(lineDetailsRepository = get()) }
  factory { LineMapModelFactory() }

  viewModel {
    BusStopsListViewModel(
      executor = get(),
      getLineBusStops = get()
    )
  }
  viewModel {
    BusStopsMapViewModel(
      executor = get(),
      getLineDetails = get(),
      lineMapModelFactory = get()
    )
  }
}
