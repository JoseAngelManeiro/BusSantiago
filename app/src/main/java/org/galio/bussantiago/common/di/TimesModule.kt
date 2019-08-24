package org.galio.bussantiago.common.di

import org.galio.bussantiago.features.times.AddBusStopFavorite
import org.galio.bussantiago.features.times.TimesFactory
import org.galio.bussantiago.features.times.TimesViewModel
import org.galio.bussantiago.features.times.GetBusStopRemainingTimes
import org.galio.bussantiago.features.times.RemoveBusStopFavorite
import org.galio.bussantiago.features.times.ValidateIfBusStopIsFavorite
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timesModule = module {
  factory { GetBusStopRemainingTimes(busStopRemainingTimesRepository = get()) }
  factory { ValidateIfBusStopIsFavorite(busStopFavoriteRepository = get()) }
  factory { AddBusStopFavorite(busStopFavoriteRepository = get()) }
  factory { RemoveBusStopFavorite(busStopFavoriteRepository = get()) }
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
