package org.galio.bussantiago.di

import org.galio.bussantiago.shared.TimesFactory
import org.galio.bussantiago.features.times.TimesViewModel
import org.galio.bussantiago.shared.TimeFormatter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timesModule = module {

  factory { TimeFormatter() }

  viewModel {
    TimesViewModel(
      executor = get(),
      getBusStopRemainingTimes = get(),
      validateIfBusStopIsFavorite = get(),
      addBusStopFavorite = get(),
      removeBusStopFavorite = get(),
      timesFactory = TimesFactory()
    )
  }
}
