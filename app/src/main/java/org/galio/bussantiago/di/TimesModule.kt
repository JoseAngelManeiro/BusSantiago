package org.galio.bussantiago.di

import org.galio.bussantiago.domain.interactor.AddBusStopFavorite
import org.galio.bussantiago.features.times.TimesFactory
import org.galio.bussantiago.features.times.TimesViewModel
import org.galio.bussantiago.domain.interactor.GetBusStopRemainingTimes
import org.galio.bussantiago.domain.interactor.RemoveBusStopFavorite
import org.galio.bussantiago.domain.interactor.ValidateIfBusStopIsFavorite
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timesModule = module {
  factory {
    GetBusStopRemainingTimes(
      busStopRemainingTimesRepository = get()
    )
  }
  factory {
    ValidateIfBusStopIsFavorite(
      busStopFavoriteRepository = get()
    )
  }
  factory {
    AddBusStopFavorite(
      busStopFavoriteRepository = get()
    )
  }
  factory {
    RemoveBusStopFavorite(
      busStopFavoriteRepository = get()
    )
  }
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
