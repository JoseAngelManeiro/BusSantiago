package org.galio.bussantiago.di

import org.galio.bussantiago.features.stops.map.BusStopsMapViewModel
import org.galio.bussantiago.features.stops.map.LineMapModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val busStopsMapModule = module {

  factory { LineMapModelFactory() }

  viewModel {
    BusStopsMapViewModel(
      executor = get(),
      getLineDetails = get(),
      lineMapModelFactory = get()
    )
  }
}
