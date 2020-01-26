package org.galio.bussantiago.di

import org.galio.bussantiago.features.favorites.FavoritesViewModel
import org.galio.bussantiago.domain.interactor.GetBusStopFavorites
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
  factory {
    GetBusStopFavorites(
      busStopFavoriteRepository = get()
    )
  }
  viewModel {
    FavoritesViewModel(
      executor = get(),
      getBusStopFavorites = get()
    )
  }
}
