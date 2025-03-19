package org.galio.bussantiago.di

import org.galio.bussantiago.features.favorites.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
  viewModel { FavoritesViewModel(executor = get(), getBusStopFavorites = get()) }
}
