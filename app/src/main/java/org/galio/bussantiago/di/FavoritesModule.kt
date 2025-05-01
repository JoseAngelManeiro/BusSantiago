package org.galio.bussantiago.di

import androidx.fragment.app.Fragment
import org.galio.bussantiago.features.favorites.FavoritesViewModel
import org.galio.bussantiago.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {

  viewModel { (fragment: Fragment) ->
    FavoritesViewModel(
      executor = get(),
      getBusStopFavorites = get(),
      navigator = Navigator(fragment)
    )
  }
}
