package org.galio.bussantiago.common.di

import org.galio.bussantiago.features.times.TimesFactory
import org.galio.bussantiago.features.times.TimesViewModel
import org.galio.bussantiago.features.times.GetBusStopRemainingTimes
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timesModule = module {
  factory { GetBusStopRemainingTimes(busStopRemainingTimesRepository = get()) }
  viewModel {
    TimesViewModel(
      executor = get(),
      getBusStopRemainingTimes = get(),
      timesFactory = TimesFactory()
    )
  }
}
